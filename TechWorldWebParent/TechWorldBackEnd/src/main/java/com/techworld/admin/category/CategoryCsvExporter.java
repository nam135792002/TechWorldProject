package com.techworld.admin.category;

import com.techworld.admin.AbstractExporter;
import com.techworld.common.entity.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

public class CategoryCsvExporter extends AbstractExporter {
    public void export(List<Category> listCategories, HttpServletResponse response) throws IOException {
        super.setResponseHeaser(response,"text/csv",".csv","categories_");
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Category ID","Category Name", "Enabled"};
        String[] fieldMapping = {"id","name", "enabled"};
        csvBeanWriter.writeHeader(csvHeader);
        for(Category category : listCategories){
            csvBeanWriter.write(category,fieldMapping);
        }
        csvBeanWriter.close();
    }
}
