import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class ConcurrnetTotalFileSizeLatch {
  private ExecutorService service;
  final private AtomicLong pendingFilevists = new AtomicLong();
  final private AtomicLong TotalSize = new AtomicLong();
  final private CountDownLatch latch = new CountDownLatch(1);
  public long getTotalSizeOfFile(final String FileName)
  {
	  service = Executors.newFixedThreadPool(100);
	  pendingFilevists.getAndIncrement();
	  updateTotalSizeofFilesDir(new File(FileName));
	  try {
		latch.await(100, TimeUnit.SECONDS);
		
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally
	{
		service.shutdown();
		
	}
	  return TotalSize.longValue();
  }
  public void updateTotalSizeofFilesDir(final File file)
  {
	  long filesize =0;
	  if(file.isFile())
	  {
		  filesize+=file.length();
	  }else
	  {
		  final File[] children = file.listFiles();
		  if(children !=null)
		  {
			  for(final File child:children)
			  {
				  if(child.isFile())
				  {
					  filesize+=child.length();
				  }
				  else
				  {
					  pendingFilevists.getAndIncrement();
					  service.execute(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							updateTotalSizeofFilesDir(child);
						}
						  
					  });
				  }
			  }
		  }
	  }
	  TotalSize.addAndGet(filesize);
	  if(pendingFilevists.decrementAndGet()==0) latch.countDown();
  }
 
}
