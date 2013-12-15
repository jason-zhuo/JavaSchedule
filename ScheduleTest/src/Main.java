import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final long start = System.nanoTime();
		final ExecutorService service = Executors.newFixedThreadPool(100);
		long total = 0;
		try {
			
			ConcurrnetTotalFileSizeLatch latch = new ConcurrnetTotalFileSizeLatch();
			total =latch.getTotalSizeOfFile("/home/zzl/1TestArea");
			// total = new
			// TotalFileSizeSequential().getTotalSizeOfFileInDir(service,new
			// File("/home/zzl/1TestArea"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			service.shutdown();
		}

		final long end = System.nanoTime();
		System.out.println("Toal size : " + total);
		System.out.println("Time taken  : " + (end - start) / 1.0e9);
	}

}
