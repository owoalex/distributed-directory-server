package com.indentationerror.dds.database;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbsoluteNodePath {

    String[] pathComponents;
    public AbsoluteNodePath(String path) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        this.pathComponents = path.split("/");
    }

    /**
     * Returns the node the path refers to. If the path has a * selector, it will return null.
     * @param graphDatabase
     * @return The node if path is exact, else null
     */
    public Node getNodeFrom(GraphDatabase graphDatabase) {
        Node tailNode = null;
        int i = 0;
        int pathTraversalLength = pathComponents.length;
        if (pathComponents[0].startsWith("{") && pathComponents[0].endsWith("}")) {
            i = 1;
            Pattern pattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(pathComponents[0]);
            if (matcher.find()) {
                String uuid = matcher.group();
                tailNode = graphDatabase.getNode(UUID.fromString(uuid));
            } else {
                tailNode = null;
            }
        }
        if (tailNode != null) {
            while (i < pathTraversalLength) {
                if (pathComponents[i].length() > 0) {
                    if (tailNode != null) {
                        tailNode = tailNode.getPropertyUnsafe(pathComponents[i]);
                    }
                }
                i++;
            }
        }
        return tailNode;
    }

    @Override
    public String toString() {
        return String.join("/", pathComponents);
    }
}
