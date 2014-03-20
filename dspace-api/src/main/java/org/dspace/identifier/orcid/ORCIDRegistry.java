package org.dspace.identifier.orcid;

import java.sql.SQLException;
import org.dspace.core.Context;
import org.dspace.storage.rdbms.DatabaseManager;
import org.dspace.storage.rdbms.TableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ORCIDRegistry {
    private static final Logger log = LoggerFactory.getLogger(ORCIDRegistry.class);


    public static String lookup(Context context, String author)
            throws SQLException
    {
        String sql = "SELECT * FROM Orcid WHERE author = ?";

        TableRow orcidRow = DatabaseManager.querySingleTable(context, "Orcid",
                                                             sql, author);
        if (orcidRow == null)
            return null;

        if (orcidRow.isColumnNull("orcid")) {
            log.error("An author with an empty orcid column was found in the database: "
                    + author + ".");
            throw new IllegalStateException(
                    "An author with an empty orcid column was found in the database: "
                    + author + ".");
        }

        return orcidRow.getStringColumn("orcid");
    }
}
