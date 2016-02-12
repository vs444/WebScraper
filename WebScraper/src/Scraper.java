
import java.util.ArrayList;

import com.jaunt.*;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class Scraper {
	
	public static void main(String[] args)
	{
		try{
			UserAgent userAgent = new UserAgent();   
			userAgent.visit("https://duapp2.drexel.edu/webtms_du/app?page=Home&service=page");

			Element div = userAgent.doc.findFirst("<div class='term'>").findEvery("<a href>");
			Elements links = userAgent.doc.findEvery("<div class=term").findEvery("<a href>");
			
			ArrayList<String> termLinks = new ArrayList<String>();
			for(Element e : links)
			{
//				System.out.println(e.getText());
				String link = e.getAt("href");
				link = link.replaceAll("&amp;", "&");
//				System.out.println(link);
				termLinks.add(link);

				UserAgent agent = new UserAgent();
				agent.visit(link);
				Elements collegeDiv = agent.doc.findFirst("div id='sideLeft'").findEvery("<a href>");
				for(Element collegeLink : collegeDiv)
				{
//					System.out.println(collegeLink.getText());
					String link2 = collegeLink.getAt("href");
					link2 = link2.replaceAll("&amp;", "&");
//					System.out.println(link2);
					agent.visit(link2);
					Elements collegePanel = agent.doc.findFirst("table class=collegePanel").findEvery("<a href>");
					for(Element college : collegePanel)
					{
//						System.out.println(college.getText());
						String link3 = college.getAt("href");
						link3 = link3.replace("&amp;", "&");
//						System.out.println(link3);
						
						//link3 = subjects in a college
						agent.visit(link3);
						Elements crns = agent.doc.findFirst("<table width='99%'").findEvery("<a href>");
						for(Element crn : crns)
						{
							System.out.println(crn.getText());
							String link4 = crn.getAt("href");
							link4 = link4.replace("&amp;", "&");
							System.out.println(link4);
						}
						
					}
					
					
				}
			}
		}//find 1st anchor element with href attribute
			
		catch(JauntException e){             
			System.err.println(e);         
		}
	}
	
	

}
