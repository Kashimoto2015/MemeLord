package de.sanel.memelord.listener;

import de.sanel.memelord.manager.Manager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuildMemberJoinListener extends ListenerAdapter {

    /**
     * Event welches ausgelöst wird wenn ein neuer User dem Server beitritt
     * @param e Das Event wird hier abgespeichert
     */
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e){

        User user = e.getUser();
        Manager.sendDirectMessage(user, "Hallo lieber " + user.getAsMention() + ",");
        Manager.sendDirectMessage(user, "vielen Dank dass du dem Server " + e.getGuild().getName() + " beigetreten bist!");
        Manager.sendDirectMessage(user, "Hier hast du eine Übersicht mit allen Befehlen die dieser Bot verwendet:");
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor("Hier findest du alle Befehle des Bots!");
        embedBuilder.setTitle("Hilfe");
        embedBuilder.setColor(Color.ORANGE);
        embedBuilder.addField("-ml sayhello", "Mit diesem Befehl begrüßt dich der Bot", false);
        embedBuilder.addField("-ml say <TEXT>", "Mit dem Befehl kannst du dem Bot befehlen etwas zu sagen", false);
        embedBuilder.addField("-ml deleteall", "Wenn du ein Admin bist kannst du mit dem Befehl alle Nachrichten aus einem Channel löschen", false);
        embedBuilder.addField("-ml setstandardchannel", "Wenn du ein Admin bist kannst du mit dem Befehl dem Bot den Standardchannel für den Bot mitteilen", false);
        Manager.sendDirectMessage(user, embedBuilder.build());

        Role admin = null;

        for (Role role : e.getGuild().getRoles()){
            if(role.hasPermission(Permission.ADMINISTRATOR)){
                admin = role;
                break;
            }
        }

        assert admin != null;
        Manager.sendDirectMessage(user, "Wenn du weitere Fragen hast, dann melde dich gerne bei den @" + admin.getName() + " melden.");
        Manager.sendDirectMessage(user, "Wir wünschen dir noch einen schönen Tag!");

    }

}
