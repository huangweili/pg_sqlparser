/*
 * Copyright (c) Splendid Data Product Development B.V. 2020
 *
 * This program is free software: You may redistribute and/or modify under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at Client's option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, Client should obtain one via www.gnu.org/licenses/.
 */

package com.splendiddata.sqlparser.structure;

import java.util.LinkedList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.splendiddata.sqlparser.ParserUtil;
import com.splendiddata.sqlparser.enums.NodeTag;
import com.splendiddata.sqlparser.enums.ObjectType;

/**
 * Copied from /postgresql-9.3.4/src/include/nodes/parsenodes.h
 *
 * @author Splendid Data Product Development B.V.
 * @since 0.0.1
 */
@XmlRootElement(namespace = "parser")
public class AlterExtensionContentsStmt extends Node {
    /** Extension's name */
    @XmlAttribute
    public String extname;

    /** +1 = add object, -1 = drop object */
    @XmlAttribute
    public int action;

    /** Object's type */
    @XmlAttribute
    public ObjectType objtype;

    /**
     * Qualified name of the object
     * 
     * @deprecated The objname is not part of the AlterExtensionContentsStmt beginning in posrgres version 10.
     */
    @XmlElementWrapper(name = "objname")
    @XmlElement(name = "nameNode")
    @Deprecated
    public List<? extends Node> objname;

    /**
     * Arguments if needed (eg, for functions)
     * 
     * @deprecated The objargs is not part of the AlterExtensionContentsStmt beginning in posrgres version 10.
     */
    @XmlElementWrapper(name = "objargs")
    @XmlElement(name = "objarg")
    @Deprecated
    public List<Node> objargs;

    /**
     * Qualified name of the object
     * 
     * @since 5.0
     */
    @XmlElement
    public Node object;

    /**
     * Constructor
     */
    public AlterExtensionContentsStmt() {
        super(NodeTag.T_AlterExtensionContentsStmt);
    }

    /**
     * Copy constructor
     *
     * @param original
     *            The AlterExtensionContentsStmt to copy
     */
    public AlterExtensionContentsStmt(AlterExtensionContentsStmt original) {
        super(original);
        this.extname = original.extname;
        this.action = original.action;
        this.objtype = original.objtype;
        if (original.object != null) {
            this.object = original.object.clone();
        }
        if (original.objname != null) {
            throw new IllegalArgumentException(AlterExtensionContentsStmt.class.getName()
                    + ".objname is not supported any more since version 5.0 (Postgres version 10)), contains: "
                    + original.objname);
        }
        if (original.objargs != null) {
            throw new IllegalArgumentException(AlterExtensionContentsStmt.class.getName()
                    + ".objargs is not supported any more since version 5.0 (Postgres version 10)), contains: "
                    + original.objargs);
        }
    }

    @Override
    public AlterExtensionContentsStmt clone() {
        AlterExtensionContentsStmt clone = (AlterExtensionContentsStmt) super.clone();
        if (objname != null) {
            clone.objname = objname.clone();
        }
        if (objargs != null) {
            clone.objargs = objargs;
        }
        if (object != null) {
            clone.object = object.clone();
        }
        return clone;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("alter extension ").append(ParserUtil.identifierToSql(extname));

        if (action > 0) {
            result.append(" add ");
        } else {
            result.append(" drop ");
        }

        result.append(objtype);

        switch (objtype) {
        case OBJECT_CAST:
            result.append(' ');

            result.append('(').append(ParserUtil.nameToSql((TypeName) ((List) object).get(0))).append(" as ")
                    .append(ParserUtil.nameToSql((TypeName) ((List) object).get(0))).append(')');
            break;
        case OBJECT_OPCLASS:
        case OBJECT_OPFAMILY:
            LinkedList<Value> objNameList = new LinkedList<>((List<Value>) object);
            Value indexMethod = objNameList.removeFirst();
            result.append(ParserUtil.nameListToSql(objNameList));
            result.append(" using ").append(ParserUtil.identifierToSql(indexMethod.val.str));
            break;
        case OBJECT_OPERATOR:
            result.append(ParserUtil.nameToSql(object));
            break;
        case OBJECT_DOMAIN:
            result.append(ParserUtil.nameToSql(object));
            break;
        case OBJECT_TYPE:
            result.append(ParserUtil.nameToSql(object));
            break;
        default:
            result.append(' ').append(ParserUtil.nameToSql(object));
            break;
        }

        return result.toString();
    }
}
