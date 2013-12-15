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
  long getTotalSizeOfFileInDir(final ExecutorService service, final File file) throws InterruptedException, ExecutionException, TimeoutException
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

 
}
