package com.yr.management.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.management.common.entity.QueryRequestPage;
import com.yr.management.generator.entity.Column;
import com.yr.management.generator.entity.Table;

import java.util.List;

/**
 * @author dengbp
 */
public interface IGeneratorService {

    List<String> getDatabases(String databaseType);

    IPage<Table> getTables(String tableName, QueryRequestPage request, String databaseType, String schemaName);

    List<Column> getColumns(String databaseType, String schemaName, String tableName);
}
