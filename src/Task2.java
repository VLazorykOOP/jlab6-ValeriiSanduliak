import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Task2 {
    private JFrame frame;
    private JTextField filePathField;
    private JTextField resMaxField;
    private JTextField resMinField;
    private JTable matrixTable;

    public Task2() {
        frame = new JFrame();
        frame.setTitle("Matrix Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel filePathLabel = new JLabel("File Path:");
        filePathField = new JTextField("D:\\Education\\2-course\\Java\\jlab6-ValeriiSanduliak\\files\\matrix.txt", 20);
        JButton openButton = new JButton("Open");
        openButton.addActionListener(new OpenButtonListener());
        topPanel.setBackground(Color.GRAY);
        topPanel.add(filePathLabel);
        topPanel.add(filePathField);
        topPanel.add(openButton);
        frame.add(topPanel, BorderLayout.NORTH);

        matrixTable = new JTable();
        matrixTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(matrixTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JLabel leb1 = new JLabel("Column with the maximum sum of modules:");
        resMaxField = new JTextField(10);
        JLabel leb2 = new JLabel("The smallest value in this column:");
        resMinField = new JTextField(10);
        bottomPanel.add(leb1);
        bottomPanel.add(resMaxField);
        bottomPanel.add(leb2);
        bottomPanel.add(resMinField);
        bottomPanel.setBackground(Color.GRAY);

        frame.add(bottomPanel, BorderLayout.AFTER_LAST_LINE);
        frame.pack();
        frame.setVisible(true);
    }

    private class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            try {
                int[][] matrix = readMatrixFromFile(filePath);
                showMatrix(matrix);
                minimumColumnElement(matrix);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "File: " + filePath + " not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid data format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (CustomException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int[][] readMatrixFromFile(String filePath) throws FileNotFoundException, NumberFormatException, CustomException {
        File file = new File(filePath);
        int[][] matrix = new int[10][10];
        int row = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] numbers = line.split("\\s+");

                if (numbers.length > 10) {
                    throw new CustomException("Invalid matrix size!");
                }

                for (int col = 0; col < numbers.length; col++) {
                    try {
                        matrix[row][col] = Integer.parseInt(numbers[col]);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException();
                    }
                }
                row++;
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        if (row > 10) {
            throw new CustomException("Invalid matrix size!");
        }
        return matrix;
    }

    private void showMatrix(int[][] matrix) {
        DefaultTableModel model = new DefaultTableModel();
        for (int col = 0; col < 10; col++) {
            model.addColumn(col);
        }
        for (int row = 0; row < 10; row++) {
            Integer[] rowData = new Integer[10];
            for (int col = 0; col < 10; col++) {
                rowData[col] = matrix[row][col];
            }
            model.addRow(rowData);
        }
        matrixTable.setModel(model);
    }

    private void minimumColumnElement(int[][] matrix) {
        int n = 10;
        int maxSumOfAbs = 0;
        int maxSumOfAbsColumn = -1;
        for (int j = 0; j < n; j++) {
            int sumOfAbs = 0;
            for (int i = 0; i < n; i++) {
                sumOfAbs += Math.abs(matrix[i][j]);
            }
            if (sumOfAbs > maxSumOfAbs) {
                maxSumOfAbs = sumOfAbs;
                maxSumOfAbsColumn = j;
            }
        }
        int min = 0;
        for (int i = 0; i < n; i++) {
            int element = matrix[i][maxSumOfAbsColumn];
            if (element < min) {
                min = element;
            }
        }
        resMaxField.setText(Integer.toString(maxSumOfAbsColumn));
        resMinField.setText(Integer.toString(min));
    }

    private static class CustomException extends ArithmeticException {
        public CustomException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        new Task2();
    }
}
