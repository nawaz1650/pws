package com.shahnawaz.pws.export;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.shahnawaz.pws.entities.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Orders {


List<Order> orders;






        private XSSFWorkbook workbook;
        private XSSFSheet sheet;

        public Orders(List<Order> orders) {
            super();
            this.orders = orders;
            workbook=new XSSFWorkbook();
            sheet=workbook.createSheet("Order history");
            writeHeader();



        }
        public void writeHeader() {
            Row row=sheet.createRow(1);

            Cell orderidcell=row.createCell(1);
            orderidcell.setCellValue("Order id");

            Cell qtycell=row.createCell(2);
            qtycell.setCellValue("Quantity");

            Cell adresscell=row.createCell(3);
            adresscell.setCellValue("Address");

            Cell created_date=row.createCell(4);
            created_date.setCellValue("for date");

            Cell updated_date=row.createCell(5);
            updated_date.setCellValue("ordered_date");
            writeDataRow();

        }

        void writeDataRow(){
            int rowindex=2;
            CreationHelper creationhelper=workbook.getCreationHelper();

            for(Order order:orders) {
                int colindex=1;
                Row row=sheet.createRow(rowindex);

                Cell orderid=row.createCell(colindex++);
                orderid.setCellValue(order.getOrder_id());

                Cell qtycell=row.createCell(colindex++);
                qtycell.setCellValue(order.getQty());


                Cell addresscell=row.createCell(colindex++);
                addresscell.setCellValue(order.getAddress());

                Cell order_date=row.createCell(colindex++);
                order_date.setCellValue(order.getOrder_date());
                CellStyle style=workbook.createCellStyle();
                style.setDataFormat(creationhelper.createDataFormat().getFormat("dd-mm-yy hh:mm;ss"));
                order_date.setCellStyle(style);
                //created_date.setCellValue(todo.getCreated_date());

                Cell created_date=row.createCell(colindex++);
                created_date.setCellValue(order.getCreated_date());
                created_date.setCellStyle(style);
                rowindex++;

            }
        }
        public void export(HttpServletResponse response) throws IOException {

            ServletOutputStream stream=response.getOutputStream();
            workbook.write(stream);

            workbook.close();


            stream.close();


        }



}
