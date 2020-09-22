package de.sanel.memelord.listener;

import de.sanel.memelord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ReceiveMessageListener extends ListenerAdapter {

    /**
     * @param e Das Event wird hier abgespeichert
     * Mit dieser Methode fangen wir ab wenn ein User eine Nachricht schreibt
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent e){

        String message = e.getMessage().getContentDisplay();

        if(e.isFromType(ChannelType.TEXT)){

            TextChannel channel = e.getTextChannel();
            Message msg = e.getMessage();
            Member member = e.getMember();

            /*
             * Hier wird abgefragt, ob die Nachricht mit dem Prefix -ml anfängt
             */
            if(message.startsWith("-ml ")){

                String[] args = message.substring(4).split(" ");

                /*
                 * Hier wird abgefragt ob die Nachricht nur 1 Argument hat
                 */
                if(args.length == 1) {

                    /*
                     * Das ist der Teil für den Help-Command
                     */
                    if (args[0].equalsIgnoreCase("help")){

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setAuthor("Hier findest du alle Befehle des Bots!");
                        embedBuilder.setTitle("Hilfe");
                        embedBuilder.setColor(Color.ORANGE);
                        embedBuilder.setImage("https://cdn.discordapp.com/avatars/756777414327009351/a703f503e1e819dfd123e742467c10d6.png");
                        embedBuilder.addField("-ml sayhello", "Mit diesem Befehl begrüßt dich der Bot", false);
                        embedBuilder.addField("-ml say <TEXT>", "Mit dem Befehl kannst du dem Bot befehlen etwas zu sagen", false);
                        embedBuilder.addField("-ml deleteall", "Wenn du ein Admin bist kannst du mit dem Befehl alle Nachrichten aus einem Channel löschen", false);
                        embedBuilder.addField("-ml setstandardchannel", "Wenn du ein Admin bist kannst du mit dem Befehl dem Bot den Standardchannel für den Bot mitteilen", false);
                        channel.sendMessage(embedBuilder.build()).queue();

                    }

                    /*
                     * Das ist der Teil für den Sayhello-Command
                     */
                    if (args[0].equalsIgnoreCase("sayhello")) {

                        if (isStandardChannel(channel, msg)) {

                            EmbedBuilder embedBuilder = new EmbedBuilder();
                            embedBuilder.setTitle("Meme-Lord");
                            embedBuilder.setColor(Color.ORANGE);
                            embedBuilder.setDescription("Hello, " + e.getAuthor().getAsMention());
                            channel.sendMessage(embedBuilder.build()).queue();

                        }

                    }

                    /*
                     * Das ist der Teil für den Deleteall-Command
                     */
                    if (args[0].equalsIgnoreCase("deleteall")) {

                        if (isStandardChannel(channel, msg)) {

                            if(hasPermission("756789896299020331", member)) {
                                List<Message> messages = channel.getHistory().retrievePast(100).complete();
                                channel.deleteMessages(messages).complete();
                                channel.sendMessage(member.getAsMention() + " hat alle Nachrichten gelöscht!").queue();
                            } else {
                                channel.sendMessage(member.getAsMention() + ", du hast keine Berechtigung dazu!").queue();
                            }

                        }

                    }

                    /*
                     * Das ist der Teil für den SetStandardChannel-Command
                     */
                    if (args[0].equalsIgnoreCase("setstandardchannel")) {

                        if(hasPermission("756789896299020331", member)){

                            Main.setStandardChannel(channel);
                            channel.sendMessage("Du hast "+ channel.getAsMention() + " zum Standardchannel des Servers gemacht!").queue();

                        }

                    }

                }

                /*
                 * Hier wird abgefragt ob die Nachricht mehr als 1 Argument hat
                 */
                if(args.length > 1){

                    /*
                     * Das ist der Teil für den Say-Command
                     */
                    if (args[0].equalsIgnoreCase("say")) {

                        if (isStandardChannel(channel, msg)) {

                            String sendMessage = "";

                            for(int i = 0; i < args.length; i++){

                                if(i != 0){

                                    sendMessage += args[i] + " ";

                                }

                            }

                            EmbedBuilder embedBuilder = new EmbedBuilder();
                            embedBuilder.setTitle("Meme-Lord");
                            embedBuilder.setColor(Color.ORANGE);
                            embedBuilder.setDescription(sendMessage);
                            channel.sendMessage(embedBuilder.build()).queue();

                        }

                    }

                }

                /*
                 * Hier wird abgefragt ob die Nachricht nur 2 Argument hat
                 */
                if(args.length == 2){

                    /*
                     * Das ist der Teil für den Kick-Command
                     */
                    if (args[0].equalsIgnoreCase("kick")) {

                        if (isStandardChannel(channel, msg)) {

                            if(member.hasPermission(Permission.ADMINISTRATOR)){

                                if(e.getGuild().getMemberByTag(args[1]) != null) {

                                    e.getGuild().kick(e.getGuild().getMemberByTag(args[1]));

                                } else
                                    channel.sendMessage("Du musst einen User angeben wenn du jemanden kicken möchtest!").queue();

                            } else
                                channel.sendMessage(e.getAuthor().getAsMention() + ", du hast dazu keine Berechtigung!").queue();

                        }

                    }
                }

            }

        }

    }

    /**
     * @param member Das ist der User welcher nach der Rolle abgefragt werden soll
     * @param id Das ist die ID der Rolle
     * @return Boolean Hier wird zurückgegeben, ob der User die Rolle hat oder nicht
     */
    public Role findRole(Member member, String id) {
        List<Role> roles = member.getRoles();
        return roles.stream()
                .filter(role -> role.getId().equals(id)) // filter by role name
                .findFirst() // take first result
                .orElse(null); // else return null
    }

    /**
     * @param pId Id der Rolle
     * @param member User welcher überprüft wird
     * @return Boolean Ob der User ein Permission hat oder nicht
     */
    public boolean hasPermission(String pId, Member member){

        if(findRole(member, pId) != null)
            return true;
        else
            return false;

    }

    /**
     * @param channel - TextChannel welcher überprüft werden soll ob er ein Botchannel ist
     * @param msg - Die vom User gesendete Nachricht um Sie ggf. zu löschen
     * @return boolean - Ob der User eine Nachricht in einem Botchannel geschrieben hat oder nicht
     */
    public boolean isStandardChannel(TextChannel channel, Message msg){

        if(Main.getStandardChannel() != null) {
            if (channel.getId().equalsIgnoreCase(Main.getStandardChannel().getId())) {
                return true;
            } else {
                channel.sendMessage("Das hier ist nicht der richtige Channel für den Befehl, bitte gehe nach " + Main.getStandardChannel().getAsMention()).queue();
                return false;
            }
        } else {
            msg.delete().queue();
            channel.sendMessage("Es wurde noch kein Standardchannel gesetzt!").queue();
            return false;
        }

    }

}
