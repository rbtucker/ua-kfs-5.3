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
package org.kuali.kfs.module.tem.dataaccess;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.kfs.module.tem.businessobject.AccountingDocumentRelationship;
import org.kuali.kfs.sys.ConfigureContext;
import org.kuali.kfs.sys.context.KualiTestBase;
import org.kuali.kfs.sys.context.SpringContext;

@ConfigureContext
public class AccountingDocumentRelationshipDaoTest extends KualiTestBase {

    private AccountingDocumentRelationshipDao dao;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = SpringContext.getBean(AccountingDocumentRelationshipDao.class);
        dao.save(new AccountingDocumentRelationship("test1","test2"));
        dao.save(new AccountingDocumentRelationship("test2","test3"));
        dao.save(new AccountingDocumentRelationship("test2","test4"));
    }

    /**
     *
     * This method tests {@link AccountingDocumentRelationshipDao#save(AccountingDocumentRelationship)}.
     */
    @Test
    public void testSave(){
        //test save with incomplete AccountingDocumentRelationship
        AccountingDocumentRelationship adr = new AccountingDocumentRelationship("test1",null);
        dao.save(adr);
        assertNull(adr.getId());

        //test save with an existing AccountingDocumentRelationship
        adr = new AccountingDocumentRelationship("test1","test2");
        dao.save(adr);
        assertNull(adr.getId()); // this relationship already exists so document relationship will not save

        //test save with a valid AccountingDocumentRelationship
        adr = new AccountingDocumentRelationship("test4","test5");
        dao.save(adr);
        assertNotNull(adr.getId());
    }

    /**
     *
     * This method tests {@link AccountingDocumentRelationshipDao#findAccountingDocumentRelationshipByDocumentNumber(String, String)}.
     */
    @Test
    public void testFindAccountingDocumentRelationshipByDocumentNumber(){
        //test find using bad documentNumber
        List<AccountingDocumentRelationship> adrList = dao.findAccountingDocumentRelationshipByDocumentNumber(AccountingDocumentRelationship.DOC_NBR, "-1");
        assertTrue(adrList.isEmpty());

        //test find using existing documentNumber
        adrList = dao.findAccountingDocumentRelationshipByDocumentNumber(AccountingDocumentRelationship.DOC_NBR, "test1");
        assertTrue(!adrList.isEmpty());
    }

    /**
     *
     * This method tests {@link AccountingDocumentRelationshipDao#findAccountingDocumentRelationship(AccountingDocumentRelationship)}.
     */
    @Test
    public void testFindAccountingDocumentRelationship(){
        //test find using documentNumber
        AccountingDocumentRelationship adr = new AccountingDocumentRelationship("test2",null);
        List<AccountingDocumentRelationship> adrList = dao.findAccountingDocumentRelationship(adr);
        assertTrue(adrList.size() == 2);

        //test find using documentNumber and relDocumentNumber
        adr.setRelDocumentNumber("test3");
        adrList = dao.findAccountingDocumentRelationship(adr);
        assertTrue(adrList.size() == 1);
    }
}
