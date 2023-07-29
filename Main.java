import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        String[] routes = new String[1000];
        for (int i = 0; i < routes.length; i++) {
            routes[i] = generateRoute("RLRFR", 100);
        }

        for (String route : routes) {
            Runnable logic = () -> {
                int countR = 0;
                for (int i = 0; i < route.length(); i++) {
                    if (route.charAt(i) == 'R') {
                        countR++;
                    }
                }
                System.out.println(route.substring(0, 100) + " -> " + countR);

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(countR)) {
                        sizeToFreq.put(countR, sizeToFreq.get(countR) + 1);
                    } else {
                        sizeToFreq.put(countR, 1);
                    }
                }
            };

            Thread myThread = new Thread(logic);
            threads.add(myThread);
            myThread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int maxValue = 0;
        int maxKey = 0;
        for (int key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxValue) {
                maxValue = sizeToFreq.get(key);
                maxKey = key;
            }
        }
        System.out.println("Самое частое количество повторений " + maxKey + " (встретилось " + maxValue + " раз)");

        System.out.println("Другие размеры:");
        for (int key : sizeToFreq.keySet()) {
            if (key != maxKey) {
                int value = sizeToFreq.get(key);
                System.out.println("- " + key + " (" + value + " раз)");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}