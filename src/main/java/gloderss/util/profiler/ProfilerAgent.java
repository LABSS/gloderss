package gloderss.util.profiler;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class ProfilerAgent implements ClassFileTransformer {
	
	protected Instrumentation	instrumentation	= null;
	
	
	public static void premain(String agentArgs, Instrumentation inst) {
		new ProfilerAgent(inst);
	}
	
	
	public ProfilerAgent(Instrumentation inst) {
		this.instrumentation = inst;
		this.instrumentation.addTransformer(this);
	}
	
	
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		
		if("gloderss/engine/devs/EventSimulator".equals(className)) {
			try {
				ClassPool cp = ClassPool.getDefault();
				CtClass cc = cp.get("gloderss.engine.devs.EventSimulator");
				CtMethod m = cc.getDeclaredMethod("doAllEvents");
				m.addLocalVariable("elapsedTime", CtClass.longType);
				m.insertBefore("elapsedTime = System.currentTimeMillis();");
				m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
						+ "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");
				byte[] byteCode = cc.toBytecode();
				cc.detach();
				return byteCode;
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return null;
	}
}