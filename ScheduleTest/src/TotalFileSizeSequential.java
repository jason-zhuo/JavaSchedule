import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;  
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TotalFileSizeSequential {
// private long getTotalSizeOfFileInDir(final File file)
// {
//	 if(file.isFile()) return file.length();
//	 
//	 final File[] children =file.listFiles();
//	 long total =0;
//	 if(children!=null)
//		 for (final File child: children)
//			 total += getTotalSizeOfFileInDir(child);
//	 
//	return total;	 
// }
 private long getTotalSizeOfFileInDir(final ExecutorService service, final File file) throws InterruptedException, ExecutionException, TimeoutException
 {
	 List<Future<Long>> partialTotoalFutures = null;
	 if(file.isFile()) return file.length();
	 
	 final File[] children =file.listFiles();
	 long total =0;
	 if(children!=null)
	 {
		  partialTotoalFutures= new ArrayList<Future<Long>>();
	 }
	 for (final File child: children)
	 {
		 Future<Long> futuretask = service.submit(new Callable<Long>(){

			@Override
			public Long call() throws Exception {
				// TODO Auto-generated method stub
				return getTotalSizeOfFileInDir(service,child);
			}});
		 partialTotoalFutures.add(futuretask); 
		 
	 }
		 
	 for (final Future<Long> a : partialTotoalFutures)
	  {
		  total += a.get(100, TimeUnit.SECONDS);
	  }
	return total;	 
 }
 public static void main (final String args[]) throws InterruptedException, ExecutionException, TimeoutException
 {
	 final long start = System.nanoTime();
	 final ExecutorService service= Executors.newFixedThreadPool(100);
	 long total=0;
	 try 
	 {
		  total = new TotalFileSizeSequential().getTotalSizeOfFileInDir(service,new File("/home/zzl/1TestArea"));
	 }finally
	 {
		 service.shutdown();
	 }
	 
	 final long end = System.nanoTime();
	 System.out.println("Toal size : "+ total);
	 System.out.println("Time taken  : "+ (end -start)/1.0e9);
 }
 
}
