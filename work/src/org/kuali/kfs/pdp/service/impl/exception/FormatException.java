/*
 * The Kuali Financial System, a comprehensive financial management system for higher education.
 * 
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on Aug 24, 2004
 *
 */
package org.kuali.kfs.pdp.service.impl.exception;

public class FormatException extends RuntimeException {
    
    public FormatException() {
        super();
    }

    public FormatException(String arg0) {
        super(arg0);
    }

    public FormatException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public FormatException(Throwable arg0) {
        super(arg0);
    }
}
