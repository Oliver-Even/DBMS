package mydbms;

/**
 * 展示文件内容
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTablelist {
    private static int PADDING = 2;//间距为2
    private static String NEWLINE = "\n";//换行
    private static String DIVISION = "+";//分割表头与值
    private static String VERTICAL_SEGMENTATION = " ";//纵向分割符
    private static String HORIZONTAL_SEGMENTATION = "-";//横向分隔符

    /**
     * 根据传入内容，展示文件内容
     *
     */
    public static String generateTable(List<String> headersList, List<List<String>> rowsList,int... overRiddenHeaderHeight) {//可传多个数值
        StringBuilder stringbuilder = new StringBuilder();//动态对象，用于存储最终的输出结果
        int rowheight = 0;
        if(overRiddenHeaderHeight.length > 0)
        	rowheight = overRiddenHeaderHeight[0];
        else
        	rowheight = 1;
        //得到表最大的宽度
        Map<Integer, Integer> colMaxWidthMapping = getMaxWidhtofTable(headersList, rowsList);
        createRowLine(stringbuilder, headersList.size(), colMaxWidthMapping);
        stringbuilder.append(NEWLINE);//得到第一行分隔行再添加换行符
        //添加头的内容
        for (int i = 0; i < headersList.size(); i++) {
            fillCell(stringbuilder, headersList.get(i), i, colMaxWidthMapping);
        }
        //第二行的分隔行
        stringbuilder.append(NEWLINE);
        createRowLine(stringbuilder, headersList.size(), colMaxWidthMapping);
        //添加值的内容
        for (List<String> row : rowsList) {
            for (int i = 0; i < rowheight; i++) {
                stringbuilder.append(NEWLINE);
            }

            for (int i = 0; i < row.size(); i++) {
                fillCell(stringbuilder, row.get(i), i, colMaxWidthMapping);
            }
        }

        stringbuilder.append(NEWLINE);
        createRowLine(stringbuilder, headersList.size(), colMaxWidthMapping);
        return stringbuilder.toString();//输出最终的展示结果
    }

    /**
     * 根据长度，追加空格
     * 
     */
    private static void fillSpace(StringBuilder stringBuilder, int length) {
        for (int i = 0; i < length - 1; i++) {
            stringBuilder.append(" ");//追加空格
        }
    }
	/**
	 * 添加分隔符，得到分隔行
	 *
	 */
    private static void createRowLine(StringBuilder stringBuilder, int headersListSize,Map<Integer, Integer> colMaxWidthMapping) {
        for (int i = 0; i < headersListSize; i++) {
            if (i == 0) {
                stringBuilder.append(DIVISION);//在最开始先加上分隔，两个表头之间分隔的加号
            }

            for (int j = 0; j < colMaxWidthMapping.get(i) + PADDING * 2; j++) {
                stringBuilder.append(HORIZONTAL_SEGMENTATION);
            }
            stringBuilder.append(DIVISION);
        }
    }

    /**
     * 得到表的最大宽度
     * 
     */
    private static Map<Integer, Integer> getMaxWidhtofTable(List<String> headersList, List<List<String>> rowsList) {
        Map<Integer, Integer> colMaxWidthMapping = new HashMap<>();

        //初始化把头的长度为零 对应key为头的次序，值为零
        for (int i = 0; i < headersList.size(); i++) {
            colMaxWidthMapping.put(i, 0);
        }

        //当有头的值是存放头的长度
        for (int i = 0; i < headersList.size(); i++) {
            if (headersList.get(i).length() > colMaxWidthMapping.get(i)) {
                colMaxWidthMapping.put(i, headersList.get(i).length());
            }
        }

        //当每行的值的长度大与表头的长度时，替换原来的值，得到最大值
        for (List<String> row : rowsList) {

            for (int i = 0; i < row.size(); i++) {//每行

                if (row.get(i).length() > colMaxWidthMapping.get(i)) {
                    colMaxWidthMapping.put(i, row.get(i).length());
                }
            }
        }

        for (int i = 0; i < headersList.size(); i++) {

        	//如果长度是奇数，则长度加一
            if (colMaxWidthMapping.get(i) % 2 != 0) {
                colMaxWidthMapping.put(i, colMaxWidthMapping.get(i) + 1);
            }
        }

        return colMaxWidthMapping;
    }
	/**
	 * 得到最佳区间内边距
	 * 
	 */
    private static int getOptimumCellPadding(int cellIndex, int datalength, Map<Integer, Integer> colMaxWidthMapping,int cellPaddingSize) {
        if (datalength % 2 != 0) {
            datalength++;
        }

        if (datalength < colMaxWidthMapping.get(cellIndex)) {//返回key所对应的值
            cellPaddingSize = cellPaddingSize + (colMaxWidthMapping.get(cellIndex) - datalength) / 2;
        }

        return cellPaddingSize;
    }

    /**
     * 设置左右两边的分隔符
     * 
     */
    private static void fillCell(StringBuilder stringBuilder, String cell, int cellIndex,
                                 Map<Integer, Integer> colMaxWidthMapping) {
    	//得到最好的边距
        int cellPaddingSize = getOptimumCellPadding(cellIndex, cell.length(), colMaxWidthMapping, PADDING);

        //如果是第一位则直接加上左分隔
        if (cellIndex == 0) {
            stringBuilder.append(VERTICAL_SEGMENTATION);
        }

        fillSpace(stringBuilder, 2);
        stringBuilder.append(cell);
        if (cell.length() % 2 != 0) {
            stringBuilder.append(" ");
        }

        fillSpace(stringBuilder, cellPaddingSize);
        fillSpace(stringBuilder, cellPaddingSize);
        fillSpace(stringBuilder, 2);

        stringBuilder.append(VERTICAL_SEGMENTATION);
    }

}
