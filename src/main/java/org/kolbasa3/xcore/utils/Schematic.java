package org.kolbasa3.xcore.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.kolbasa3.xcore.XCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class Schematic {

    static HashMap<String, EditSession> editSessions = new HashMap<>();

    public void paste(Location loc, String key) {
        Clipboard clipboard;
        File file1 = new File(XCore.getInstance().getDataFolder(), "schematics");
        File file = new File(file1, key+".schem");
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        if(format == null) return;
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(loc.getWorld()))) {
            editSessions.put(key, editSession);
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }

    public void undo(String key) {
        if(!editSessions.containsKey(key)) return;
        EditSession esn2 = editSessions.get(key);
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(esn2.getWorld())) {
            esn2.undo(editSession);
        }
        editSessions.remove(key);
    }
}
