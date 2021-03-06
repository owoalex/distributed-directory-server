package com.indentationerror.dds.conditions;

import com.indentationerror.dds.database.GraphDatabase;
import com.indentationerror.dds.database.GraphDatabaseBacking;
import com.indentationerror.dds.database.NodePathContext;

public class LogicalNotCondition extends LogicalCondition {
    private Condition condition;

    public LogicalNotCondition(GraphDatabase graphDatabase, Condition condition) {
        super(graphDatabase);
        this.condition = condition;
    }

    @Override
    public boolean eval(NodePathContext context) {
        return !condition.eval(context);
    }
}
