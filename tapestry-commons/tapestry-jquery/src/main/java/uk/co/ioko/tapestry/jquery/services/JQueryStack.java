package uk.co.ioko.tapestry.jquery.services;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StylesheetLink;

import java.util.Collections;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: bgidley
 * Date: 01/06/2011
 * Time: 08:57
 */
public class JQueryStack implements JavaScriptStack {

    private final List<Asset> javaScriptStack;

    public JQueryStack(final AssetSource assetSource, @Symbol("tapestry.production-mode") Boolean productionMode) {

        Mapper<String, Asset> pathToAsset = new Mapper<String, Asset>() {
            public Asset map(String path) {
                return assetSource.getExpandedAsset(path);
            }
        };

        Mapper<String, StylesheetLink> pathToStylesheetLink = pathToAsset
                .combine(TapestryInternalUtils.assetToStylesheetLink);

        String jquery;
        if (productionMode) {
            jquery = "uk/co/ioko/tapestry/jquery/pages/jquery-1.6.1.min.js";
        } else {
            jquery = "uk/co/ioko/tapestry/jquery/pages/jquery-1.6.1.js";
        }
        javaScriptStack = F
                .flow(jquery, "uk/co/ioko/tapestry/jquery/pages/jquery-noconflict.js")
                .map(pathToAsset).toList();
    }

    public List<String> getStacks() {
        return Collections.emptyList();
    }

    public List<Asset> getJavaScriptLibraries() {
        return javaScriptStack;
    }

    public List<StylesheetLink> getStylesheets() {
        return Collections.emptyList();
    }

    public String getInitialization() {
        return null;
    }
}
