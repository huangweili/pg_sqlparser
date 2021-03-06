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

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.splendiddata.sqlparser.ParserUtil;
import com.splendiddata.sqlparser.enums.NodeTag;

/**
 * Copied from /postgresql-9.3.4/src/include/nodes/parsenodes.h
 *
 * @author Splendid Data Product Development B.V.
 * @since 0.0.1
 */
@XmlRootElement(namespace = "parser")
public class DropdbStmt extends Node {
    /** database to drop */
    @XmlAttribute
    public String dbname;

    /** skip error if db is missing? */
    @XmlAttribute
    public boolean missing_ok;

    /**
     * currently only FORCE is supported
     * 
     * @since 8.0 - Postgres version 13
     */
    @XmlElementWrapper(name = "options")
    @XmlElement(name = "option")
    public List<DefElem> options;

    /**
     * Constructor
     */
    public DropdbStmt() {
        super(NodeTag.T_DropdbStmt);
    }

    /**
     * Copy constructor
     *
     * @param original
     *            The DropdbStmt to copy
     */
    public DropdbStmt(DropdbStmt original) {
        super(original);
        this.dbname = original.dbname;
        this.missing_ok = original.missing_ok;
        if (original.options != null) {
            this.options = original.options.clone();
        }
    }

    /**
     * @see com.splendiddata.sqlparser.structure.Node#clone()
     *
     * @return DropdbStmt The deep clone
     */
    @Override
    public DropdbStmt clone() {
        DropdbStmt clone = (DropdbStmt) super.clone();
        if (options != null) {
            clone.options = this.options.clone();
        }
        return clone;
    }

    /**
     * @see java.lang.Object#toString()
     *
     * @return String the original statement
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("drop database ");
        if (missing_ok) {
            result.append("if exists ");
        }
        result.append(ParserUtil.identifierToSql(dbname));
        if (options != null) {
            String separator = " (";
            for (DefElem option : options) {
                result.append(separator).append(option.defname);
                separator = ", ";
            }
            result.append(')');
        }
        return result.toString();
    }
}
