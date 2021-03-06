package com.indentationerror.dds.query;

import com.indentationerror.dds.conditions.Condition;
import com.indentationerror.dds.database.*;

import java.text.ParseException;
import java.util.*;

public class DirectoryQuery extends Query {
    protected DirectoryQuery(String src, Node actor) throws ParseException {
        super(src, actor);
    }

    public Map<String, Node> runDirectoryQuery(GraphDatabase graphDatabase) throws NoSuchMethodException, QueryException {
        if (this.queryType != QueryType.DIRECTORY) {
            throw new NoSuchMethodException();
        }

        Node[] valueNodes = new RelativeNodePath(parsedQuery.poll()).getMatchingNodes(new SecurityContext(graphDatabase, this.actor), new NodePathContext(this.actor, null), graphDatabase.getAllNodes());
        Node valueNode = (valueNodes.length == 1) ? valueNodes[0] : null;

        if (valueNode == null) {
            throw new QueryException(QueryExceptionCode.MISSING_SUBJECT);
        }

        //return valueNode.getProperties(new SecurityContext(graphDatabase, this.actor));
        return valueNode.getPropertiesUnsafe();
    }
}
