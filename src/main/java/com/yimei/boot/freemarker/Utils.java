package com.yimei.boot.freemarker;

import freemarker.template.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 12/2/14.
 */
public class Utils {
    public static class Param {
        public static String getAsString(Map params, String name) {
            Object ret = params.get(name);
            if (ret != null)
                return ((SimpleScalar) ret).getAsString();
            return null;
        }

        public static Integer getAsInteger(Map params, String name) {
            Object ret = params.get(name);
            if (ret != null && ret instanceof SimpleNumber) {
                return ((SimpleNumber) ret).getAsNumber().intValue();
            }
            return null;
        }

        public static Integer getAsInteger(Map params, String name, int defaultValue) {
            Integer ret = getAsInteger(params, name);
            return ret != null ? ret : defaultValue;
        }

        public static TemplateHashModelEx getAsHash(Map params, String name) {
            Object ret = params.get(name);
            if (ret != null)
                return (TemplateHashModelEx) ret;
            return null;
        }
    }

    public static Map<String, String> templateHashToMap(TemplateHashModelEx hash) throws TemplateModelException {
        Map<String, String> map = new HashMap<>();
        TemplateModelIterator it = hash.keys().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            map.put(key, hash.get(key).toString());
        }
        return map;
    }

    public static List<NameValuePair> templateHashToList(TemplateHashModelEx hash) throws TemplateModelException {
        if (hash != null) {
            ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
            TemplateModelIterator it = hash.keys().iterator();
            while (it.hasNext()) {
                String key = it.next().toString();
                list.add(new BasicNameValuePair(key, hash.get(key).toString()));
            }
            return list;
        }
        return null;
    }

    public static URI makeURI(String path, List<NameValuePair> params) {
        String url = path;
        if (params != null && params.size() > 0) {
            url = url + "?" + URLEncodedUtils.format(params, "utf-8");
        }
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URI makeURI(String path, TemplateHashModelEx params) throws TemplateModelException {
        return makeURI(path, templateHashToList(params));
    }

    public static URI makeURI(String path, Map<String, String> params) {
        List<NameValuePair> list = null;
        if (params != null && params.size() > 0) {
            list = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return makeURI(path, list);
    }
}
