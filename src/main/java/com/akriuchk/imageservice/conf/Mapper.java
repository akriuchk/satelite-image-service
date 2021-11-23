package com.akriuchk.imageservice.conf;

import com.akriuchk.imageservice.model.Feature;
import com.akriuchk.imageservice.model.Image;
import com.akriuchk.imageservice.service.Parser;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Mapper {

    public static Feature map(Parser.PreFeature pre) {
        final Parser.PreFeature.InFeature.InProperty properties = pre.getFeatures().iterator().next().getProperties();

        return Feature.builder()
                .uuid(UUID.fromString(properties.getId()))
                .timestamp(properties.getTimestamp())
                .beginViewingDate(properties.getAcquisition().getBeginViewingDate())
                .endViewingDate(properties.getAcquisition().getEndViewingDate())
                .missionName(properties.getAcquisition().getMissionName())
                .build();
    }

    public static Image extract(Parser.PreFeature pre) {
        final Parser.PreFeature.InFeature.InProperty properties = pre.getFeatures().iterator().next().getProperties();

        if (properties.getQuicklook() == null) {
            return null;
        }

        Image img = new Image();
        img.setUuid(UUID.fromString(properties.getId()));
        img.setContent(properties.getQuicklook().getBytes(StandardCharsets.UTF_8));

        return img;
    }
}
