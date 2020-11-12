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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.splendiddata.sqlparser.enums.NodeTag;

/**
 * Copied from /postgresql-9.3.4/src/include/nodes/parsenodes.h
 *
 * @author Splendid Data Product Development B.V.
 * @since 0.0.1
 */
@XmlRootElement(namespace = "parser")
public class WithClause extends Node {

    /** list of CommonTableExprs */
    @XmlElementWrapper(name = "ctes")
    @XmlElement(name = "cte")
    public List<CommonTableExpr> ctes;

    /** true = WITH RECURSIVE */
    @XmlAttribute
    public boolean recursive;

    /**
     * Constructor
     */
    public WithClause() {
        super(NodeTag.T_WithClause);
    }

    /**
     * Copy constructor
     *
     * @param original
     *            The WithClause to copy
     */
    public WithClause(WithClause original) {
        super(original);
        if (original.ctes != null) {
            this.ctes = original.ctes.clone();
        }
        this.recursive = original.recursive;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String separator = "with ";
        if (recursive) {
            separator = "with recursive ";
        }
        for (CommonTableExpr obj : ctes) {
            result.append(separator).append(obj);
            separator = ", ";
        }
        return result.toString();
    }

    @Override
    public WithClause clone() {
        WithClause clone = (WithClause) super.clone();
        if (ctes != null) {
            clone.ctes = ctes.clone();
        }
        return clone;
    }
}
