/**
 * Author by guojian, Email guojian_k@qq.com, Date on 2018/6/19 8:30 AM
 * PS: Not easy to write code, please indicate.
 */
package guojian.smart.snake;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Model {

    public static final int APPLE = -1;
    public static final int BLANK = 0;
    public static final int WALL = -2;
    public static final int BODY = 1;
    public static final int TAIL = 1;

    /**
     * 贪食蛇画面中的地图
     * world为view中渲染的数据来源
     */
    public int[][] world;
    private int[][] snake;
    private int[][] walls;
    /**
     * 苹果的坐标
     * [row,col]
     */
    private int[] apples;
    private int[] head;
    /**
     * 地图宽
     */
    public static final int COLS = Conf.WIDTH;
    /**
     * 地图高
     */
    public static final int ROWS = Conf.HEIGHT;

    /**
     * 蛇的长度
     */
    private int count;
    private boolean running;
    /***
     * 蛇下一步要走的方向
     */
    private Direction direction;

    public void moveUp() {
        direction = Direction.UP;
    }

    public void moveDown() {
        direction = Direction.DOWN;
    }

    public void moveLeft() {
        direction = Direction.LEFT;
    }

    public void moveRight() {
        direction = Direction.RIGHT;
    }


    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }


    public Model() {
        running = false;
        direction = Direction.UP;
        world = initIntArray(ROWS, COLS, BLANK);
        initSnake();
        initWalls();
        apples = randomApple(snake, walls);
        updateWorld(world, snake, walls, apples);
    }

    private void updateWorld(int[][] world, int[][] snake, int[][] walls, int[] apples) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                // 清空
                world[row][col]=BLANK;

                // 从新生成
                if (walls[row][col] == WALL) {
                    world[row][col] = WALL;
                } else {
                    if (snake[row][col] != BLANK) {
                        world[row][col] = BODY;
                    }
                }
            }
        }
        world[apples[0]][apples[1]] = APPLE;
    }

    /**
     * 随机生成苹果坐标
     *
     * @param snake
     * @param walls
     * @return
     */
    private int[] randomApple(int[][] snake, int[][] walls) {
        ArrayList<int[]> list = new ArrayList(ROWS * COLS);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (walls[row][col] == BLANK && snake[row][col] == BLANK) {
                    list.add(new int[]{row, col});
                }
            }
        }
        Random r = new Random();
        int index = r.nextInt(list.size());

        return list.get(index);
    }


    private void initWalls() {
        walls = initIntArray(ROWS, COLS, BLANK);
        for (int row = 0; row < ROWS; row++) {
            walls[row][0] = WALL;
            walls[row][COLS - 1] = WALL;
        }
        for (int col = 0; col < COLS; col++) {
            walls[0][col] = WALL;
            walls[ROWS - 1][col] = WALL;
        }
    }

    private void initSnake() {
        count = 2;
        snake = initIntArray(ROWS, COLS, BLANK);
        snake[ROWS / 2][COLS / 2] = TAIL;
        snake[(ROWS / 2) - 1][COLS / 2] = count;
        head = new int[2];
        head[0] = ROWS / 2 - 1;
        head[1] = COLS / 2;
    }


    private int[][] initIntArray(int rows, int cols, int b) {
        int[][] temp = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                temp[row][col] = b;
            }
        }
        return temp;
    }

    /**
     * 改变游戏状态，运行或暂停
     */
    public void changeState() {
        running = !running;
    }


    /**
     * 更新world数据
     */
    public void update() {
        if (running) {
            System.out.println(new Date());
            int[] tmpHead = new int[]{head[0], head[1]};
            int[][] tmpWorld = initIntArray(ROWS, COLS, BLANK);
            updateWorld(tmpWorld, snake, walls, apples);
            switch (direction) {
                case RIGHT:
                    tmpHead[1]++;
                    break;
                case LEFT:
                    tmpHead[1]--;
                    break;
                case DOWN:
                    tmpHead[0]++;
                    break;
                case UP:
                    tmpHead[0]--;
                    break;
            }
            int type = tmpWorld[tmpHead[0]][tmpHead[1]];
            switch (type) {
                case BLANK:
                    snakeMove(snake, tmpHead, head);
                    head[0] = tmpHead[0];
                    head[1] = tmpHead[1];
                    updateWorld(world, snake, walls, apples);
                    break;
                case WALL:
                    gameOver();
                    System.out.println("Wall");
                    break;
                case BODY:
                    gameOver();
                    System.out.println("body");
                    break;
                case APPLE:
                    eatApple(snake, tmpHead, head);
                    count = snake[tmpHead[0]][tmpHead[1]];
                    head[0] = tmpHead[0];
                    head[1] = tmpHead[1];
                    apples = randomApple(snake, walls);
                    updateWorld(world, snake, walls, apples);
                    break;
                default:
                    return;
            }

        }
    }

    private void eatApple(int[][] snake, int[] tmpHead, int[] head) {
        // 注意使用++ 和 --的时候，会改变数组中的值。
        // 此处使用+1 而不能使用++
        snake[tmpHead[0]][tmpHead[1]] = snake[head[0]][head[1]]+1;
    }

    private void gameOver() {
        running = false;
        System.out.println("gameover");
    }

    private void snakeMove(int[][] snake, int[] tmphead, int[] head) {
        int tmp = snake[head[0]][head[1]];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (snake[row][col] != BLANK) {
                    snake[row][col]--;
                }
            }
        }
        snake[tmphead[0]][tmphead[1]] = tmp;
    }

}
