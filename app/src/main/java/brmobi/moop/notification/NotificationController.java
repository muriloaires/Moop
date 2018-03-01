package brmobi.moop.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import brmobi.moop.R;
import brmobi.moop.data.network.model.Usuario;
import brmobi.moop.service.AprovarService;
import brmobi.moop.ui.main.MoopActivity;

/**
 * Created by murilo aires on 17/12/2017.
 */

public enum NotificationController {

    I;

    private static final String KEY_PERFIL = "perfil";
    private static final String KEY_TITULO = "titulo";
    private static final String KEY_CONDOMINIO = "condominio";
    private static final String KEY_MENSAGEM = "mensagem";
    private static final String KEY_TIPO = "tipo";
    private static final String KEY_ID_OBJ = "idObj";
    private static final String KEY_PERFIL_TO = "perfilTo";

    public static final String TIPO_NOVA_MENSAGEM = "nova_mensagem";
    public static final String TIPO_NOVO_COMENTARIO_FEED = "novo_comentario_feed";
    public static final String TIPO_SOLICITACAO_MORADOR = "nova_solicitacao_para_morador_liberar";
    public static final String TIPO_NOVO_USUARIO_LIBERADO = "novo_usuario_liberado";
    public static final String TIPO_NOVA_CURTIDA = "nova_curtida_feed";

    public void handleRemoteMessage(RemoteMessage message, Context context) {
        switch (message.getData().get(KEY_TIPO)) {
            case TIPO_NOVA_MENSAGEM:
                handleNovaMensagem(message, context);
                break;
            case TIPO_NOVO_COMENTARIO_FEED:
                handleNovoComentarioFeed(message, context);
                break;
            case TIPO_SOLICITACAO_MORADOR:
                handleNovoMorador(message, context);
                break;
            case TIPO_NOVO_USUARIO_LIBERADO:
                handleNovoMoradorLiberado(message, context);
                break;
        }

    }

    private void handleNovoMoradorLiberado(RemoteMessage message, Context context) {
        Usuario from = new Gson().fromJson(message.getData().get(KEY_PERFIL), Usuario.class);
        if (from != null) {
            String title = from.getNome();
            Intent intent = new Intent(context, MoopActivity.class);
            intent.putExtra("condominio_id", message.getData().get(KEY_CONDOMINIO));
            intent.putExtra("action", "novo_morador_liberado");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(message.getData().get(KEY_MENSAGEM))
                    .setSmallIcon(R.drawable.ic_add)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }

    private void handleNovoMorador(RemoteMessage message, Context context) {
        String title = "Novo Morador";

        Intent aprovarIntent = new Intent(context, AprovarService.class);
        aprovarIntent.setAction(AprovarService.Companion.getACTION_APROVAR());
        aprovarIntent.putExtra(AprovarService.Companion.getPARAM_PERFIL_HAB_ID(), message.getData().get(KEY_ID_OBJ));
        PendingIntent piAprovar = PendingIntent.getService(context, 0, aprovarIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent desaprovarIntent = new Intent(context, AprovarService.class);
        desaprovarIntent.setAction(AprovarService.Companion.getACTION_DESAPROVAR());
        desaprovarIntent.putExtra(AprovarService.Companion.getPARAM_PERFIL_HAB_ID(), message.getData().get(KEY_ID_OBJ));
        PendingIntent piDesaprovar = PendingIntent.getService(context, 0, desaprovarIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(context, MoopActivity.class);
        intent.putExtra("condominio_id", message.getData().get(KEY_CONDOMINIO));
        intent.putExtra("action", "novo_morador");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(message.getData().get(KEY_MENSAGEM))
                .setSmallIcon(R.drawable.ic_add)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setContentIntent(pendingIntent)
                .addAction(new NotificationCompat.Action(R.drawable.ic_thumb_up, "Aprovar", piAprovar))
                .addAction(new NotificationCompat.Action(R.drawable.ic_thumb_down, "Não Aprovar", piDesaprovar))
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) Long.parseLong(message.getData().get(KEY_ID_OBJ)) /* ID of notification */, notificationBuilder.build());
    }

    private void handleNovoComentarioFeed(RemoteMessage message, Context context) {
        Usuario from = new Gson().fromJson(message.getData().get(KEY_PERFIL), Usuario.class);
        if (from != null) {
            String title = from.getNome() + " comentou na sua publicação";
            Intent intent = new Intent(context, MoopActivity.class);
            intent.putExtra("condominio_id", message.getData().get(KEY_CONDOMINIO));
            intent.putExtra("action", "comentarios");
            intent.putExtra("feed_id", message.getData().get(KEY_ID_OBJ));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(message.getData().get(KEY_MENSAGEM))
                    .setSmallIcon(R.drawable.ic_add)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }


    private void handleNovaMensagem(RemoteMessage message, Context context) {
        Usuario from = new Gson().fromJson(message.getData().get(KEY_PERFIL), Usuario.class);
        if (from != null) {
            String title = from.getNome();
            Intent intent = new Intent(context, MoopActivity.class);
            intent.putExtra("condominio_id", message.getData().get(KEY_CONDOMINIO));
            intent.putExtra("action", "mensagens");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(message.getData().get(KEY_MENSAGEM))
                    .setSmallIcon(R.drawable.ic_add)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }
}
