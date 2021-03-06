package com.dotmarketing.portlets.contentlet.transform;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.dotcms.contenttype.model.field.BinaryField;
import com.dotcms.contenttype.model.field.Field;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.DotStateException;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.util.Config;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.UtilMethods;

/**
 * DBTransformer that converts DB objects into Contentlet instances
 */
public class BinaryToMapTransformer implements FieldsToMapTransformer {
    final Map<String, Object> mapOfMaps;



    public BinaryToMapTransformer(final Contentlet con) {

        if (con.getInode() == null) {
            throw new DotStateException("Contentlet needs an inode to get fields");
        }
        final Map<String, Object> newMap = new HashMap<>();
        if (con.getContentType().fields() != null) {
            for (final Field field : con.getContentType().fields()) {
                if (field instanceof BinaryField) {
                    try {
                        newMap.put(field.variable(), con.getBinary(field.variable()).getName());
                    } catch (IOException e) {
                        Logger.warn(this, "Unable to get Binary from field with var " + field.variable());
                    }
                    newMap.put(field.variable()+"Map", transform(field, con));
                }
            }
        }


        this.mapOfMaps = newMap;
    }

    @Override
    public Map<String, Object> asMap() {
        return this.mapOfMaps;
    }



    @NotNull
    private Map<String, Object> transform(final Field field, final Contentlet con) {
        File file;
        try {
            file = con.getBinary(field.variable());
        } catch (IOException e) {
            throw new DotStateException(e);
        }

        final Map<String, Object> map = new HashMap<>();

        map.put("versionPath", "/dA/" + APILocator.getShortyAPI().shortify(con.getInode()) + "/" + field.variable() + "/" + file.getName());
        map.put("idPath", "/dA/" + APILocator.getShortyAPI().shortify(con.getIdentifier()) + "/" + field.variable() + "/" + file.getName());
        map.put("name", file.getName());
        map.put("size", file.length());
        map.put("mime", Config.CONTEXT.getMimeType(file.getName()));
        map.put("isImage", UtilMethods.isImage(file.getName()));

        return map;
    }
}

