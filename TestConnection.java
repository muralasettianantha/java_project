
package db;
import java.sql.Connection;

public class TestConnection {
   public TestConnection() {
   }

   public static void main(String[] var0) {
      Connection var1 = DBConnection.getConnection();
      if (var1 != null) {
         System.out.println("Database Connected Successfully");
      } else {
         System.out.println("Connection Failed");
      }

   }
}
