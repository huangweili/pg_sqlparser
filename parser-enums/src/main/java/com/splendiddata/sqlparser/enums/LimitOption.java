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

package com.splendiddata.sqlparser.enums;

/**
 * LimitOption - LIMIT option of query
 * <p>
 * copied from postgresql-13beta1/src/include/nodes/nodes.h
 *
 * @author Splendid Data Product Development B.V.
 * @since 8.0 - Postgres version 13
 */
public enum LimitOption {
    /** FETCH FIRST... ONLY */
    LIMIT_OPTION_COUNT,
    /** FETCH FIRST... WITH TIES */
    LIMIT_OPTION_WITH_TIES,
    /** No limit present */
    LIMIT_OPTION_DEFAULT;

    /**
     * String containing all values with "|" characters between them, that can be used as argument in a regular
     * expression.
     */
    public static final String REPLACEMENT_REGEXP_PART;

    static {
        StringBuilder format = new StringBuilder();
        String separator = "";
        for (LimitOption type : values()) {
            format.append(separator).append(type);
            separator = "|";
        }
        REPLACEMENT_REGEXP_PART = format.toString();
    }
}
