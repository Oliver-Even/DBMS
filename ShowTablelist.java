package mydbms;

/**
 * չʾ�ļ�����
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTablelist {
    private static int PADDING = 2;//���Ϊ2
    private static String NEWLINE = "\n";//����
    private static String DIVISION = "+";//�ָ��ͷ��ֵ
    private static String VERTICAL_SEGMENTATION = " ";//����ָ��
    private static String HORIZONTAL_SEGMENTATION = "-";//����ָ���

    /**
     * ���ݴ������ݣ�չʾ�ļ�����
     *
     */
    public static String generateTable(List<String> headersList, List<List<String>> rowsList,int... overRiddenHeaderHeight) {//�ɴ������ֵ
        StringBuilder stringbuilder = new StringBuilder();//��̬�������ڴ洢���յ�������
        int rowheight = 0;
        if(overRiddenHeaderHeight.length > 0)
        	rowheight = overRiddenHeaderHeight[0];
        else
        	rowheight = 1;
        //�õ������Ŀ��
        Map<Integer, Integer> colMaxWidthMapping = getMaxWidhtofTable(headersList, rowsList);
        createRowLine(stringbuilder, headersList.size(), colMaxWidthMapping);
        stringbuilder.append(NEWLINE);//�õ���һ�зָ�������ӻ��з�
        //���ͷ������
        for (int i = 0; i < headersList.size(); i++) {
            fillCell(stringbuilder, headersList.get(i), i, colMaxWidthMapping);
        }
        //�ڶ��еķָ���
        stringbuilder.append(NEWLINE);
        createRowLine(stringbuilder, headersList.size(), colMaxWidthMapping);
        //���ֵ������
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
        return stringbuilder.toString();//������յ�չʾ���
    }

    /**
     * ���ݳ��ȣ�׷�ӿո�
     * 
     */
    private static void fillSpace(StringBuilder stringBuilder, int length) {
        for (int i = 0; i < length - 1; i++) {
            stringBuilder.append(" ");//׷�ӿո�
        }
    }
	/**
	 * ��ӷָ������õ��ָ���
	 *
	 */
    private static void createRowLine(StringBuilder stringBuilder, int headersListSize,Map<Integer, Integer> colMaxWidthMapping) {
        for (int i = 0; i < headersListSize; i++) {
            if (i == 0) {
                stringBuilder.append(DIVISION);//���ʼ�ȼ��Ϸָ���������ͷ֮��ָ��ļӺ�
            }

            for (int j = 0; j < colMaxWidthMapping.get(i) + PADDING * 2; j++) {
                stringBuilder.append(HORIZONTAL_SEGMENTATION);
            }
            stringBuilder.append(DIVISION);
        }
    }

    /**
     * �õ���������
     * 
     */
    private static Map<Integer, Integer> getMaxWidhtofTable(List<String> headersList, List<List<String>> rowsList) {
        Map<Integer, Integer> colMaxWidthMapping = new HashMap<>();

        //��ʼ����ͷ�ĳ���Ϊ�� ��ӦkeyΪͷ�Ĵ���ֵΪ��
        for (int i = 0; i < headersList.size(); i++) {
            colMaxWidthMapping.put(i, 0);
        }

        //����ͷ��ֵ�Ǵ��ͷ�ĳ���
        for (int i = 0; i < headersList.size(); i++) {
            if (headersList.get(i).length() > colMaxWidthMapping.get(i)) {
                colMaxWidthMapping.put(i, headersList.get(i).length());
            }
        }

        //��ÿ�е�ֵ�ĳ��ȴ����ͷ�ĳ���ʱ���滻ԭ����ֵ���õ����ֵ
        for (List<String> row : rowsList) {

            for (int i = 0; i < row.size(); i++) {//ÿ��

                if (row.get(i).length() > colMaxWidthMapping.get(i)) {
                    colMaxWidthMapping.put(i, row.get(i).length());
                }
            }
        }

        for (int i = 0; i < headersList.size(); i++) {

        	//����������������򳤶ȼ�һ
            if (colMaxWidthMapping.get(i) % 2 != 0) {
                colMaxWidthMapping.put(i, colMaxWidthMapping.get(i) + 1);
            }
        }

        return colMaxWidthMapping;
    }
	/**
	 * �õ���������ڱ߾�
	 * 
	 */
    private static int getOptimumCellPadding(int cellIndex, int datalength, Map<Integer, Integer> colMaxWidthMapping,int cellPaddingSize) {
        if (datalength % 2 != 0) {
            datalength++;
        }

        if (datalength < colMaxWidthMapping.get(cellIndex)) {//����key����Ӧ��ֵ
            cellPaddingSize = cellPaddingSize + (colMaxWidthMapping.get(cellIndex) - datalength) / 2;
        }

        return cellPaddingSize;
    }

    /**
     * �����������ߵķָ���
     * 
     */
    private static void fillCell(StringBuilder stringBuilder, String cell, int cellIndex,
                                 Map<Integer, Integer> colMaxWidthMapping) {
    	//�õ���õı߾�
        int cellPaddingSize = getOptimumCellPadding(cellIndex, cell.length(), colMaxWidthMapping, PADDING);

        //����ǵ�һλ��ֱ�Ӽ�����ָ�
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
