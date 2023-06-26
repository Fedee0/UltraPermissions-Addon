package me.fedee.upermsaddon.upermissionsaddon.group;
import org.bukkit.entity.Player;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.Argument;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.Child;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.Element;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.ElementInfo;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.OutcomingVariable;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.ScriptInstance;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.objects.Group;


public class PlayerIsInGroup extends Element {
    public PlayerIsInGroup(UltraCustomizer plugin) {
        super(plugin);
    }

    public String getName() {
        return "Player Is In Group";
    }

    public String getInternalName() {
        return "player-is-in-group";
    }

    public String getRequiredPlugin() {
        return "UltraPermissions";
    }

    public boolean isHidingIfNotCompatible() {
        return true;
    }

    public XMaterial getMaterial() {
        return XMaterial.GOLD_INGOT;
    }

    public String[] getDescription() {
        return new String[]{"Allows you to check if a player is in a group", "(UltraPermissions)"};
    }

    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{
                new Argument("player", "Player", DataType.PLAYER, elementInfo),
                new Argument("group", "Group", DataType.STRING, elementInfo)};
    }

    public OutcomingVariable[] getOutcomingVariables(final ElementInfo elementInfo) {
        return new OutcomingVariable[0];
    }

    public Child[] getConnectors(final ElementInfo elementInfo) {
        return new Child[]{
                new Child(elementInfo, "yes") {
                    public String getName() {
                        return "Is In Group";
                    }

                    public String[] getDescription() {
                        return new String[]{"Will be executed if the player", "is in the specified group"};
                    }

                    public XMaterial getIcon() {
                        return XMaterial.LIME_STAINED_GLASS_PANE;
                    }
                }, new Child(elementInfo, "no") {
            public String getName() {
                return "Doesn't Is In Group";
            }

            public String[] getDescription() {
                return new String[]{"Will be executed if the player", "doesn't is in the specified group"};
            }

            public XMaterial getIcon() {
                return XMaterial.RED_STAINED_GLASS_PANE;
            }
        }};
    }

    public void run(ElementInfo info, ScriptInstance instance) {

            UltraPermissionsAPI api = UltraPermissions.getAPI();

            Player player = (Player) getArguments(info)[0].getValue(instance);
            String groupName = (String) getArguments(info)[1].getValue(instance);

            String playername = player.getName();


            if (api.getGroups().name(groupName).isPresent()) {
                Group group = api.getGroups().name(groupName).get();
            if (group.getUsers().name(playername).isPresent()){
                getConnectors(info)[0].run(instance);
            } else {
                getConnectors(info)[1].run(instance);
                }
            }
        }
    }