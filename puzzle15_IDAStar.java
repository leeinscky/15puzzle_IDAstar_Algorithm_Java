import java.util.Scanner;
 
public class puzzle15_IDAStar{
 
    public static int search_Direction[][] = {{-1,0},{0,-1},{0,1},{1,0}};//��������˳��
    public static int start_List[] = new int[16];//�����ʼ״̬ʱ���б������û������������б�
    public static int final_List[] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};//�����ƶ�����ʱ��״̬�б�
    public static int search_Depth;//�������
	public static int depth_bound;//��ȱ߽磨���ޣ�
	public static final int iterative_Depth = 85;//������ȣ�80
    public static int newList[] = new int[iterative_Depth+8];
 
    //main����
    public static void main(String args[]){
		
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("���ã�����������ʮ�������ʼ�����16�����֣�\n*ע����Χ��0-15���������ң��������£�����֮���ÿո�ֿ�*");
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
				
				//�������㣺������ȣ������ƶ�������>= �����پ��루�����ƶ�����С������
                while( depth_bound <= iterative_Depth && !DF_Search(bufferPuzzle,finalPuzzle,0,0) )  
				{
					//��ǰ����������������޽⣬��������Ʊ߽��һ����������������һ����ȵ�����
                    depth_bound++;       
                }
                if(search_Depth!=-1)
				{	
					System.out.println("************�����������룬�����ʼ״̬���£� ************");
                    type(startPuzzle);
                }else
				{
                    System.out.println(iterative_Depth+"����޷�����Ŀ�꣬���л���");
                }
            }else
			{
                System.out.println("��Ǹ�������޽�,�����������µ�15����");
				System.out.println("\n");
				System.out.println("------------------------------------");
            }
        }
    }
	
	//DF_Search:�����������depth first search
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
				
                // ��֦����������ǰ���+��ǰ״̬��Ŀ��״̬������Ҫ�Ĳ���������������ֵ��> ��ȱ߽�,���м�֦
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
            System.out.println( "�����ƶ�����:"+(i+1) );
			System.out.println("\n");
            puzzle.exchange( puzzle.numberToSite[0][0], puzzle.numberToSite[0][1], puzzle.numberToSite[0][0] + search_Direction[newList[i]][0], puzzle.numberToSite[0][1] + search_Direction[newList[i]][1] );
            puzzle.type();
        }
    }
	
}
