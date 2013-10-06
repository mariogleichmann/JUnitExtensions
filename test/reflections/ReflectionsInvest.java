package reflections;

import java.util.Set;

import org.reflections.Reflections;

public class ReflectionsInvest {

	public static void main(String[] args) {
		
		//Reflections reflections = new Reflections( "reflections" );
		Reflections reflections = new Reflections( "" );
		
		System.out.println( "Scanning types ..." );
		
		Set<Class<?>> foundTypes = reflections.getTypesAnnotatedWith( FindMe.class );
		
		for( Class<?> type : foundTypes ){
			
			System.out.println( "type : " + type );
		}
		
	}
}
