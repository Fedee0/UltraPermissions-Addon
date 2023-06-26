package me.fedee.upermsaddon.upermissionsaddon.group;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

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

public class DeleteGroup extends Element {
    public DeleteGroup(UltraCustomizer plugin) {
        super(plugin);
    }

    public String getName() {
        return "Delete Group";
    }

    public String getInternalName() {
        return "delete-group";
    }

    public String getRequiredPlugin() {
        return "UltraPermissions";
    }

    public boolean isHidingIfNotCompatible() {
        return true;
    }

    public XMaterial getMaterial() {
        return XMaterial.BARRIER;
    }

    public String[] getDescription() {
        return new String[] { "Allows you to delete a group", "(UltraPermissions)" };
    }

    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[] { new Argument("group", "Group", DataType.STRING, elementInfo) };
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
            String groupName = (String) getArguments(info)[0].getValue(instance);

            if (api.getGroups().name(groupName).isPresent()) {
                Group g = api.getGroups().name(groupName).get();
                g.remove();
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

