package com.shahnawaz.pws.export;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.shahnawaz.pws.entities.Order;
import com.shahnawaz.pws.resBodies.ExportAdmin;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class OrdersAdminExport {
    List<ExportAdmin> orders;






    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public OrdersAdminExport(List<ExportAdmin> orders) {
        super();
        this.orders = orders;
        workbook=new XSSFWorkbook();
        sheet=workbook.createSheet("Order history");
        writeHeader();



    }
    public void writeHeader() {
        Row row=sheet.createRow(1);

        Cell useridcell=row.createCell(1);
        useridcell.setCellValue("User id");

        Cell mobilecell=row.createCell(2);
        mobilecell.setCellValue("mobile");

        Cell orderidcell=row.createCell(3);
        orderidcell.setCellValue("Order id");

        Cell qtycell=row.createCell(4);
        qtycell.setCellValue("Quantity");

        Cell addresscell=row.createCell(5);
        addresscell.setCellValue("Address");

        Cell orderdatecell=row.createCell(6);
        orderdatecell.setCellValue("For date");

        Cell createddatecell=row.createCell(7);
        createddatecell.setCellValue("ordered date");



        writeDataRow();

    }

    void writeDataRow(){
        int rowindex=2;
        CreationHelper creationhelper=workbook.getCreationHelper();

        for(ExportAdmin order:orders) {
            int colindex=1;
            Row row=sheet.createRow(rowindex);

            Cell userid=row.createCell(colindex++);
            userid.setCellValue(order.getUser_id());

            Cell mobilecell=row.createCell(colindex++);
            mobilecell.setCellValue(order.getMobile());


            Cell ordercell=row.createCell(colindex++);
            ordercell.setCellValue(order.getOrder_id());

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
