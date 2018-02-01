package com.lolimanzgame.huarongroad;


/**
 * Created by PC on 2018/1/16.
 */

class GameConfigurationsClass {

    //sound resource
    static final int DIE = 0x00000000;
    static final int HIT = 0x00000001;
    static final int POINT = 0x00000002;
    static final int SWOOSH = 0x00000003;
    static final int WING = 0x00000004;

    //sets table id
    static final int HDLM = 0x00000001;
    static final int GGZJ = 0x00000002;
    static final int SXBT = 0x00000003;
    static final int JZZC = 0x00000004;
    static final int XYCC = 0x00000005;

    //sets table
    static final int[][][] setTable =
    {
        {// [0]: row, [1]: col, [2]: isRotated
            {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}
        },
        {//   dummy     caocao    zhangfei    machao     huangzh    guanyu     zhaoyun      zu1        zu2        zu3        zu4
            {0, 0, 0}, {1, 2, 0}, {1, 1, 0}, {1, 4, 0}, {3, 1, 0}, {3, 2, 0}, {3, 4, 0}, {5, 1, 0}, {4, 2, 0}, {4, 3, 0}, {5, 4, 0}
        },
        {
            {0, 0, 0}, {1, 2, 0}, {3, 3, 1}, {4, 3, 1}, {5, 2, 1}, {3, 1, 0}, {4, 1, 1}, {1, 1, 0}, {2, 1, 0}, {1, 4, 0}, {2, 4, 0}
        },
        {
            {0, 0, 0}, {1, 1, 0}, {1, 3, 1}, {2, 3, 1}, {3, 3, 1}, {3, 1, 0}, {4, 1, 1}, {4, 3, 0}, {4, 4, 0}, {5, 1, 0}, {5, 4, 0}
        },
        {
            {0, 0, 0}, {2, 1, 0}, {5, 3, 1}, {2, 3, 0}, {3, 4, 0}, {4, 2, 0}, {1, 4, 0}, {1, 1, 0}, {1, 2, 0}, {1, 3, 0}, {5, 2, 0}
        },
        {
            {0, 0, 0}, {2, 2, 0}, {4, 2, 1}, {2, 1, 0}, {2, 4, 0}, {1, 2, 0}, {5, 2, 1}, {1, 1, 0}, {1, 4, 0}, {4, 1, 0}, {4, 4, 0}
        }
    };

    //characters
    static final int DUMMY = 0x00000000;
    static final int CAOCAO = 0x00000001;
    static final int ZHANGFEI = 0x00000002;
    static final int MACHAO = 0x00000003;
    static final int HUANGZHONG = 0x00000004;
    static final int GUANYU = 0x00000005;
    static final int ZHAOYUN = 0x00000006;
    static final int ZU_1 = 0x00000007;
    static final int ZU_2 = 0x00000008;
    static final int ZU_3 = 0x00000009;
    static final int ZU_4 = 0x0000000a;


    //blocks shapes
    static int[][] blkShape = {{0,0},{2,2},{1,2},{1,2},{1,2},{2,1},{1,2},{1,1},{1,1},{1,1},{1,1}};//{col, row}

    //block layout id
    static final int[] block_layout_id_table = {    0,
                                                    R.id.id_caocao,
                                                    R.id.id_zhangfei,
                                                    R.id.id_machao,
                                                    R.id.id_huangzhong,
                                                    R.id.id_guanyu,
                                                    R.id.id_zhaoyun,
                                                    R.id.id_zu_1,
                                                    R.id.id_zu_2,
                                                    R.id.id_zu_3,
                                                    R.id.id_zu_4
                                                };

    //block resource id table
    static final int[] block_resource_id_table = {   0,
                                                        R.mipmap.caocao,
                                                        R.mipmap.zhangfei,
                                                        R.mipmap.machao,
                                                        R.mipmap.huangzhong,
                                                        R.mipmap.guanyu,
                                                        R.mipmap.zhaoyun,
                                                        R.mipmap.zu,
                                                        R.mipmap.zu,
                                                        R.mipmap.zu,
                                                        R.mipmap.zu
                                                    };
    static final int[] block_resource_r_id_table = {   0,
                                                        R.mipmap.caocao,
                                                        R.mipmap.zhangfei_r,
                                                        R.mipmap.machao_r,
                                                        R.mipmap.huangzhong_r,
                                                        R.mipmap.guanyu,
                                                        R.mipmap.zhaoyun_r,
                                                        R.mipmap.zu,
                                                        R.mipmap.zu,
                                                        R.mipmap.zu,
                                                        R.mipmap.zu
                                                };
    static final int[] score_resource = {   R.mipmap.n0,
                                            R.mipmap.n1,
                                            R.mipmap.n2,
                                            R.mipmap.n3,
                                            R.mipmap.n4,
                                            R.mipmap.n5,
                                            R.mipmap.n6,
                                            R.mipmap.n7,
                                            R.mipmap.n8,
                                            R.mipmap.n9 };

    //class direction
    public enum Direction {
        UP      (0x0),
        DOWN    (0x1),
        LEFT    (0x2),
        RIGHT   (0x3);

        Direction(int i) {
            nInt = i;
        }
        final int nInt;
    }

    //block class
    static class Block
    {
        int mBlockID;
        class BlockPosition
        {
            int mRow;
            int mCol;
            int mWidth;
            int mHeight;
            boolean mIsRotation;
        }

        class BlockCoordination
        {
            int x;
            int y;
            int width;
            int height;
        }

        class MoveableSteps
        {
            int up;
            int down;
            int left;
            int right;
        }

        BlockPosition mBlkPos = new BlockPosition();
        BlockCoordination mBlkCoord = new BlockCoordination();
        MoveableSteps mMoveableSteps = new MoveableSteps();

        Block(int id, int row, int col, boolean isRotation)
        {
            mBlockID = id;
            mBlkPos.mRow = row;
            mBlkPos.mCol = col;
            mMoveableSteps.up = 1;
            mMoveableSteps.down = 1;
            mMoveableSteps.left = 1;
            mMoveableSteps.right = 1;

        if (!isRotation)
            {
                mBlkPos.mWidth = blkShape[id][0];
                mBlkPos.mHeight = blkShape[id][1];
            }
            else
            {
                mBlkPos.mWidth = blkShape[id][1];
                mBlkPos.mHeight = blkShape[id][0];
            }
            mBlkPos.mIsRotation = isRotation;
        }

        BlockPosition getBlkPos()
        {
            return mBlkPos;
        }

        BlockCoordination getBlkCoord(int winWidth)
        {
            /*        0.3cm                 8.0cm                 0.3cm
             *      __|__|________________________________________|__|
             * 0.3cm__|   ________________________________________   |
             *        |  |  2.0cm  |          |         |         |  |
             *        |  |                                        |  |
             *        |  |2.0cm                                   |  |
             *        |  |                                        |  |
             *        |  |_                                      _|  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |_                                      _|  |
             *        |  |                                        |  |
             * 10.0cm |  |                                        |  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |_                                      _|  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |_                                      _|  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *        |  |                                        |  |
             *      __|  |________________________________________|  |
             * 0.3cm__|______________________________________________|
             *
             *
             */
            mBlkCoord.x = (int)((winWidth*0.3/8.6)+(winWidth*2/8.6)*(mBlkPos.mCol-1));
            mBlkCoord.y = (int)((winWidth*10.6/8.6*0.3/10.6)+(winWidth*10.6/8.6*2/10.6)*(mBlkPos.mRow-1));
            mBlkCoord.width = (int)(winWidth * 8 / 8.6 / 4) * mBlkPos.mWidth;
            mBlkCoord.height = (int)(winWidth * 10.6 / 8.6 * 10 / 10.6 / 5) * mBlkPos.mHeight;
            return mBlkCoord;
        }

        void setRotation(boolean isRotation)
        {
            mBlkPos.mIsRotation = isRotation;
        }

        boolean isRotation()
        {
            return mBlkPos.mIsRotation;
        }

        MoveableSteps getMoveableSteps()
        {
            return mMoveableSteps;
        }
    }
}
