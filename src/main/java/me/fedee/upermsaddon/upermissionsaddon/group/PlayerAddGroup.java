package me.fedee.upermsaddon.upermissionsaddon.group;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.Argument;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.Child;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.DataRequester;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.DefaultChild;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.Element;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.ElementInfo;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.OutcomingVariable;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.ScriptInstance;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.objects.Group;
import me.TechsCode.UltraPermissions.storage.objects.User;


public class PlayerAddGroup extends Element {
    public PlayerAddGroup(UltraCustomizer plugin) {
        super(plugin);
    }

    public String getName() {
        return "Add Group Player";
    }

    public String getInternalName() {
        return "add-group-player";
    }

    public String getRequiredPlugin() {
        return "UltraPermissions";
    }

    public boolean isHidingIfNotCompatible() {
        return true;
    }

    public XMaterial getMaterial() {
        return XMaterial.TRIPWIRE_HOOK;
    }

    public String[] getDescription() {
        return new String[] { "Allows you to add a player to a group", "(UltraPermissions)" };
    }

    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[] { new Argument("player", "Player", DataType.PLAYER, elementInfo),
                new Argument("group", "Group", DataType.STRING, elementInfo) };
    }

    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{new OutcomingVariable("success", "Success", DataType.BOOLEAN, elementInfo)};
    }

    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[] { new DefaultChild(elementInfo, "next") };
    }

    public void run(ElementInfo info, ScriptInstance instance) {
        try {

        UltraPermissionsAPI api = UltraPermissions.getAPI();

        Player player = (Player) getArguments(info)[0].getValue(instance);
        String groupName = (String) getArguments(info)[1].getValue(instance);

         String playername = player.getName();


        if (api.getGroups().name(groupName).isPresent()){
            Group group = api.getGroups().name(groupName).get();
            if (api.getUsers().name(playername).isPresent()){
                User u = api.getUsers().name(playername).get();
                u.addGroup(group);
            } else {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4[ERROR] &c- A User with this name don't exist!"));
            }
        } else {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4[ERROR] &c- A Group with this name don't exist!"));
        }

        getOutcomingVariables(info)[0].register(instance, new DataRequester() {
            public Object request() {
                return true;
            }
        });
    } catch (Exception e) {
        getOutcomingVariables(info)[0].register(instance, new DataRequester() {
            public Object request() {
                return false;
            }
        });
    }
    getConnectors(info)[0].run(instance);

}

}







