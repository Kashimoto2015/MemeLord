package de.sanel.memelord;

import de.sanel.memelord.listener.GuildMemberJoinListener;
import de.sanel.memelord.listener.ReceiveMessageListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;

/**
 * @author Sanel Trnka
 * @version 0.1
 * @since 1
 */

public class Main {

    public static TextChannel standardChannel;
    public static TextChannel adminChannel;

    /**
     * @return TextChannel Speichert die Channel wo man den Bot verwenden darf
     */
    public static TextChannel getStandardChannel(){
        return standardChannel;
    }

    public static TextChannel getAdminChannel(){
        return adminChannel;
    }

    /**
     * @param channel Textchannel welcher als Standardchannel gesetzt werden soll
     */
    public static void setStandardChannel(TextChannel channel){
        standardChannel = channel;
    }

    public static void setAdminChannel(TextChannel channel){
        adminChannel = channel;
    }

    /**
     * @param args Augumente für den Start des Programmes
     * @throws LoginException
     * Main-Methode Hier wird der Bot gestartet
     */
    public static void main(String[] args) throws LoginException {

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken("NzU2Nzc3NDE0MzI3MDA5MzUx.X2Wx4w.wjvTu4yXJ3zLUBbNnXESTzx32A4");
        builder.setActivity(Activity.watching("Memes auf Reddit"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new ReceiveMessageListener());
        builder.addEventListeners(new GuildMemberJoinListener());
        standardChannel = null;
        adminChannel = null;

        builder.build();
    }

}