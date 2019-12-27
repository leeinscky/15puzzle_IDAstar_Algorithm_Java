import java.util.Scanner;
 
public class puzzle15_IDAStar{
 
    public static int search_Direction[][] = {{-1,0},{0,-1},{0,1},{1,0}};//搜索方向顺序
    public static int start_List[] = new int[16];//数码初始状态时的列表（根据用户的输入生成列表）
    public static int final_List[] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};//数码移动结束时的状态列表
    public static int search_Depth;//搜索深度
	public static int depth_bound;//深度边界（界限）
	public static final int iterative_Depth = 85;//迭代深度：80
    public static int newList[] = new int[iterative_Depth+8];
 
    //main函数
    public static void main(String args[]){
		
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("您好，请依次输入十五数码初始矩阵的16个数字：\n*注：范围：0-15，从左至右，从上至下，数据之间用空格分开*");
			for(int i=0; i<16; i++){
                start_List[i] = sc.nextInt();
            }
			System.out.println();
            search_Depth = -1;
            numberMatrix startPuzzle = new numberMatrix(start_List);
            numberMatrix bufferPuzzle = new numberMatrix(start_List);
            numberMatrix finalPuzzle = new numberMatrix(final_List);
			
            if(bufferPuzzle.solvable(finalPuzzle))
			{
                depth_bound = bufferPuzzle.Manhattan_Distance(finalPuzzle); 
				
				//必须满足：迭代深度（数码移动次数）>= 曼哈顿距离（数码移动的最小次数）
                while( depth_bound <= iterative_Depth && !DF_Search(bufferPuzzle,finalPuzzle,0,0) )  
				{
					//当前深度优先搜索后若无解，则深度限制边界加一（递增），进行下一层深度的搜索
                    depth_bound++;       
                }
                if(search_Depth!=-1)
				{	
					System.out.println("************根据您的输入，数码初始状态如下： ************");
                    type(startPuzzle);
                }else
				{
                    System.out.println(iterative_Depth+"深度无法到达目标，进行回溯");
                }
            }else
			{
                System.out.println("抱歉，问题无解,请重新输入新的15数码");
				System.out.println("\n");
				System.out.println("------------------------------------");
            }
        }
    }
	
	//DF_Search:深度优先搜索depth first search
    public static boolean DF_Search( numberMatrix puzzle,numberMatrix finalPuzzle ,int depth, int shift ){
        
		int distance;
		int oldRaw, oldColumn, newRaw, newColumn;
        if(depth == depth_bound)
		{
            distance = puzzle.Manhattan_Distance(finalPuzzle);
            if(distance == 0)
			{
                search_Depth=depth;
                return true;
				
            }else{
            return false;
			}
        }
		
        for(int i=0; i<=3; i++)
		{
			if(shift + i == 3 && depth > 0) continue;
            oldRaw = puzzle.numberToSite[0][0];
            oldColumn = puzzle.numberToSite[0][1];
            newRaw = oldRaw + search_Direction[i][0];
            newColumn = oldColumn +search_Direction[i][1];
			
            if(newRaw>=0 && newRaw<=3 && newColumn>=0 && newColumn<=3)
			{
                puzzle.exchange(oldRaw, oldColumn, newRaw, newColumn);
                distance = puzzle.Manhattan_Distance( finalPuzzle );
				
                // 剪枝条件：若当前深度+当前状态到目标状态至少需要的步数（即评估函数值）> 深度边界,进行剪枝
				if(depth + distance <= depth_bound) 
				{
                    newList[depth]=i;
                    if( DF_Search(puzzle,finalPuzzle,depth+1,i) ) 
					return true;
                }
                puzzle.exchange(newRaw, newColumn, oldRaw, oldColumn);
            }
        }
        return false;
    }
	
	public static void type(numberMatrix puzzle){
        puzzle.type();
        for(int i=0; i<search_Depth; i++)
		{
            System.out.println( "数码移动次数:"+(i+1) );
			System.out.println("\n");
            puzzle.exchange( puzzle.numberToSite[0][0], puzzle.numberToSite[0][1], puzzle.numberToSite[0][0] + search_Direction[newList[i]][0], puzzle.numberToSite[0][1] + search_Direction[newList[i]][1] );
            puzzle.type();
        }
    }
	
}
