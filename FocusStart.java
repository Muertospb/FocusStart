import java.io.*;

public class FocusStart {
    private String biggestTriangle = null;
    private double squareOfBT = 0.0;
    private FileReader fileReader = null;

    public static void main (String[] args) throws IOException {
        // Проверка корректности параметров запуска
        if (args.length != 2){
            System.out.println("Invalid parameters! Valid parameters: my-program in.txt out.txt");
            System.exit(0);
        }

        FocusStart fs = new FocusStart();

        // Входим в цикл создания потока чтения из файла
        while (fs.fileReader == null){
            //Проверяем наличие файла
            try {
                fs.fileReader = new FileReader(new File(args[0]));
            }
            // При отсутствии входного файла допускается ввод корректного имени файла вручную
            catch (FileNotFoundException e) {
                    System.out.println("File not found! Please try to input a correct file path: ");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    args[0] = reader.readLine();
                    reader.close();
            }
        }

        // Создаем reader для построчного чтения входного файла
        BufferedReader reader = new BufferedReader(fs.fileReader);


        // Проводим построчное считывание до конца файла
        while (reader.ready()){
            String currentTriangle = reader.readLine();
            String[] newTriangle = currentTriangle.split(" ");

            //Проверяем наличие корректных групп символов в строке
            if (newTriangle.length != 6){
                continue;
            }

            // Если количиство групп совпадает с допустимым значением вызываем метод вычисления площади
            try{
                double newTriangleSquare = fs.getTriangleSquare(newTriangle);

                // Проводим сравнение полученной площади с максимальным ранее полученным значением
                if (fs.squareOfBT < newTriangleSquare){
                    fs.squareOfBT = newTriangleSquare;
                    fs.biggestTriangle = currentTriangle;
                }
            }
            // Если в группах содержатся нецифровые значения - обрабатываем исключение и пропускаем строку
            catch (NumberFormatException n){
                n.printStackTrace();
            }
        }
        // После окончания входного файла закрываем поток ввода и выводим результат в файл
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        writer.write(fs.biggestTriangle);
        writer.close();
    }
    // Метод вычисления площади принимает массив из шести групп символов
    private double getTriangleSquare (String[] newTriangle) throws NumberFormatException{
        // Парстнг групп символов, передача исключения при ошибке
        // Если подразумеваются треугольники с большими площадями - нужно использовать long
        int x1 = Integer.parseInt(newTriangle[0]);
        int y1 = Integer.parseInt(newTriangle[1]);
        int x2 = Integer.parseInt(newTriangle[2]);
        int y2 = Integer.parseInt(newTriangle[3]);
        int x3 = Integer.parseInt(newTriangle[4]);
        int y3 = Integer.parseInt(newTriangle[5]);
        // Вычисляются стороны треугольника
        double aSide = Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2) * (y1 - y2));
        double bSide = Math.sqrt((x1 - x3)*(x1 - x3) + (y1 - y3) * (y1 - y3));
        double cSide = Math.sqrt((x2 - x3)*(x2 - x3) + (y2 - y3) * (y2 - y3));
        // Вычисляется полупериметр
        double halfPerimeter = (aSide + bSide + cSide) / 2;

        // По формуле Герона вычисляется площадь и передается в вызывающий метод
        return Math.sqrt( halfPerimeter * (halfPerimeter - aSide) * (halfPerimeter - bSide) * (halfPerimeter - cSide));
    }
}
