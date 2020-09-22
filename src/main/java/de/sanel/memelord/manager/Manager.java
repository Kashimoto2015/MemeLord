package de.sanel.memelord.manager;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class Manager {

    /**
     *
     * @param user User welcher die Nachricht bekommen soll
     * @param content Inhalt der Nachricht
     * Diese Methode sendet einem User eine private Nachricht auf Discord
     */
    public static void sendDirectMessage(User user, String content){

        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).queue();
        });

    }

    /**
     *
     * @param user User welcher die Nachricht bekommen soll
     * @param content Inhalt der Nachricht
     * Diese Methode sendet einem User eine private Nachricht auf Discord
     */
    public static void sendDirectMessage(User user, MessageEmbed content){

        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).queue();
        });

    }

}
