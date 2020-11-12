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

import java.io.PrintWriter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.splendiddata.sqlparser.enums.NodeTag;

/**
 * Copied from /postgresql-12beta2/src/include/nodes/parsenodes.h
 * <p>
 * ----------------------<br>
 * Vacuum and Analyze Statements
 * </p>
 * <p>
 * Even though these are nominally two statements, it's convenient to use just one node type for both.<br>
 * ----------------------
 * </p>
 *
 * @author Splendid Data Product Development B.V.
 * @since 0.0.1
 */
@XmlRootElement(namespace = "parser")
public class VacuumStmt extends Node {
    /**
     * do VACUUM
     * <p>
     * is duplicated in {@link com.splendiddata.sqlparser.PgSqlParser#VACOPT_VACUUM} by
     * {@link com.splendiddata.sqlparser.grammartojava.GrammarConverter#writeHeading(PrintWriter)}
     * </p>
     * 
     * @deprecated since 7.0 - Postgres 12. Vacuum options are now in a list of DefElem nodes
     */
    @Deprecated
    private static final int VACOPT_VACUUM = 1 << 0;

    /**
     * do ANALYZE
     * <p>
     * is duplicated in {@link com.splendiddata.sqlparser.PgSqlParser#VACOPT_ANALYZE} by
     * {@link com.splendiddata.sqlparser.grammartojava.GrammarConverter#writeHeading(PrintWriter)}
     * </p>
     * 
     * @deprecated since 7.0 - Postgres 12. Vacuum options are now in a list of DefElem nodes
     */
    @Deprecated
    private static final int VACOPT_ANALYZE = 1 << 1;

    /**
     * print progress info
     * <p>
     * is duplicated in {@link com.splendiddata.sqlparser.PgSqlParser#VACOPT_VERBOSE} by
     * {@link com.splendiddata.sqlparser.grammartojava.GrammarConverter#writeHeading(PrintWriter)}
     * </p>
     * 
     * @deprecated since 7.0 - Postgres 12. Vacuum options are now in a list of DefElem nodes
     */
    @Deprecated
    private static final int VACOPT_VERBOSE = 1 << 2;

    /**
     * FREEZE option
     * <p>
     * is duplicated in {@link com.splendiddata.sqlparser.PgSqlParser#VACOPT_FREEZE} by
     * {@link com.splendiddata.sqlparser.grammartojava.GrammarConverter#writeHeading(PrintWriter)}
     * </p>
     * 
     * @deprecated since 7.0 - Postgres 12. Vacuum options are now in a list of DefElem nodes
     */
    @Deprecated
    private static final int VACOPT_FREEZE = 1 << 3;

    /**
     * FULL (non-concurrent) vacuum
     * <p>
     * is duplicated in {@link com.splendiddata.sqlparser.PgSqlParser#VACOPT_FULL} by
     * {@link com.splendiddata.sqlparser.grammartojava.GrammarConverter#writeHeading(PrintWriter)}
     * </p>
     * 
     * @deprecated since 7.0 - Postgres 12. Vacuum options are now in a list of DefElem nodes
     */
    @Deprecated
    private static final int VACOPT_FULL = 1 << 4;

    /**
     * don't wait to get lock (autovacuum only)
     * <p>
     * is duplicated in {@link com.splendiddata.sqlparser.PgSqlParser#VACOPT_NOWAIT} by
     * {@link com.splendiddata.sqlparser.grammartojava.GrammarConverter#writeHeading(PrintWriter)}
     * </p>
     */
    //    private static final int VACOPT_NOWAIT = 1 << 5;

    /**
     * DISABLE_PAGE_SKIPPING option
     * <p>
     * is duplicated in {@link com.splendiddata.sqlparser.PgSqlParser#VACOPT_DISABLE_PAGE_SKIPPING} by
     * {@link com.splendiddata.sqlparser.grammartojava.GrammarConverter#writeHeading(PrintWriter)}
     * </p>
     * 
     * @deprecated since 7.0 - Postgres 12. Vacuum options are now in a list of DefElem nodes
     */
    @Deprecated
    private static final int VACOPT_DISABLE_PAGE_SKIPPING = 1 << 7;

    /**
     * list of DefElem nodes
     * 
     * @since 7.0 - Postgres 12. Used to be an ored integer
     */
    @XmlElementWrapper(name = "options")
    @XmlElement(name = "option")
    public List<DefElem> options;

    /**
     * list of VacuumRelation, or NIL for all
     * 
     * @since 6.0 - Postgres version 11
     */
    @XmlElementWrapper(name = "rels")
    @XmlElement(name = "rel")
    public List<VacuumRelation> rels;

    /**
     * true for VACUUM, false for ANALYZE
     * 
     * @since 7.0 - Postgres 12
     */
    @XmlAttribute
    public boolean is_vacuumcmd;

    /**
     * Constructor
     */
    public VacuumStmt() {
        super(NodeTag.T_VacuumStmt);
    }

    /**
     * Copy constructor
     * 
     * @param toCopy
     *            The VacuumStmt to copy
     */
    public VacuumStmt(VacuumStmt toCopy) {
        super(toCopy);
        if (toCopy.options != null) {
            this.options = toCopy.options.clone();
        }
        if (toCopy.rels != null) {
            this.rels = toCopy.rels.clone();
        }
        this.is_vacuumcmd = toCopy.is_vacuumcmd;
    }

    @Override
    public VacuumStmt clone() {
        VacuumStmt clone = (VacuumStmt) super.clone();
        if (options != null) {
            clone.options = options.clone();
        }
        if (rels != null) {
            clone.rels = rels.clone();
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (is_vacuumcmd) {
            result.append("vacuum");
        } else {
            result.append("analyze");
        }

        String separator = " (";
        if (options != null && !options.isEmpty()) {
            for (DefElem option : options) {
                result.append(separator).append(option.defname);
                separator = ", ";
                if (option.arg != null) {
                    result.append(' ').append(option.arg);
                }
            }
            result.append(')');
        }

        separator = " ";
        if (rels != null) {
            for (VacuumRelation rel : rels) {
                result.append(separator).append(rel);
                separator = ", ";
            }
        }

        return result.toString();
    }
}
