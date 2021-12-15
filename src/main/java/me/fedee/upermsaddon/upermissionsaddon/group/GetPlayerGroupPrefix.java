package me.fedee.upermsaddon.upermissionsaddon.group;
import org.bukkit.entity.Player;
import java.util.Optional;


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
import me.TechsCode.UltraPermissions.storage.objects.User;
import me.TechsCode.UltraPermissions.storage.objects.Group;


public class GetPlayerGroupPrefix extends Element {
    public GetPlayerGroupPrefix(UltraCustomizer plugin) {
        super(plugin);
    }

    public String getName() {
        return "Get Player Group Prefix";
    }

    public String getInternalName() {
        return "get-player-group-prefix";
    }

    public boolean isHidingIfNotCompatible() {
        return false;
    }

    public XMaterial getMaterial() {
        return XMaterial.TRIPWIRE_HOOK;
    }

    public String[] getDescription() {
        return new String[] { "Allows you to add a player to a group", "(UltraPermissions)" };
    }

    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[] { new Argument("player", "Player", DataType.PLAYER, elementInfo) };
    }

    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{new OutcomingVariable("prefix", "Prefix", DataType.STRING, elementInfo)};
    }

    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[] { new DefaultChild(elementInfo, "next") };
    }

    public void run(ElementInfo info, ScriptInstance instance) {

            UltraPermissionsAPI api = UltraPermissions.getAPI();

            Player player = (Player) getArguments(info)[0].getValue(instance);


            getOutcomingVariables(info)[0].register(instance, new DataRequester() {
                public Object request() {

                      Optional<User> user = api.getUsers().uuid(player.getUniqueId());
                       if (user.isPresent()) {
                           User u = user.get();
                           Group g = u.getActiveGroups().bestToWorst().get(0);
                           if (g.getPrefix().isPresent()) {
                               return g.getPrefix().get();
                           }
                       }

                       return null;
                       }
            });
            getConnectors(info)[0].run(instance);
    }

}







