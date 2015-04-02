

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;


public class Cricinfo{
	
	Elements m_potMatch,m_potSection,m_potStadium,m_potDate,m_time,m_potLiveScore;
	
	public Cricinfo(String url) {
		 org.jsoup.nodes.Document doc;
		try {
			 doc = Jsoup.connect(url).get();
			//File input = new File("index2.html");
			 //System.out.println(doc.toString());
			 //doc = Jsoup.parse(input, "utf-8");
			 m_potSection = doc.getElementsByClass("matches-day-block");
			 m_potMatch = doc.getElementsByClass("play_team");
			 m_potStadium = doc.getElementsByClass("play_stadium");
			 m_potDate = doc.getElementsByClass("fixture_date");
			// m_potLiveScore = doc.getElementsByClass("espni-livescores-scoreline");
			 m_potLiveScore = doc.getElementsByClass("scoreline-list");
			 m_time = doc.getElementsByClass("large-20 medium-20 columns");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getToss() {
		 List m_potnodes = m_potSection.get(0).getElementsByClass("match-status");

		 for(int i=0;i<m_potnodes.size();i++){
			Element l1 =  (Element) m_potnodes.get(i);
			TextNode t = l1.child(0).textNodes().get(0);
			System.out.println(t.toString());
		 }
	}

	public void printschdule() {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0,j=0;i<m_potMatch.size();i++,j=j+2){
			String teams = m_potMatch.get(i).textNodes().get(0).toString();
			int x = teams.indexOf("-");
			int y = teams.indexOf("v");
			sb.append(i+1).append(",");
			sb.append("\"");
			sb.append(teams.substring(x+1, y).trim()).append("\"").append(",");
			sb.append(teams.substring(y+1).trim()).append(",");
			
			sb.append("NULL").append(",");
			
			
			String date = m_potDate.get(j).textNodes().get(0).toString();
			String time = m_potDate.get(j+1).textNodes().get(0).toString();
			sb.append("2015-");
			if(date.contains("Jan")){
				sb.append("01-");
			}
			else if(date.contains("Feb")){
				sb.append("02-");
			}
			else if(date.contains("Mar")){
				sb.append("03-");
			}
			else if(date.contains("Apr")){
				sb.append("04-");
			}
			else if(date.contains("May")){
				sb.append("05-");
			}
			else if(date.contains("Jun")){
				sb.append("06-");
			}
			else if(date.contains("Jul")){
				sb.append("07-");
			}
			else if(date.contains("Aug")){
				sb.append("08-");
			}
			else if(date.contains("Sep")){
				sb.append("09-");
			}
			else if(date.contains("Oct")){
				sb.append("10-");
			}
			else if(date.contains("Nov")){
				sb.append("11-");
			}
			else if(date.contains("Dec")){
				sb.append("12-");
			}
			
			String n1 = date.substring(9).trim().toString();
			int foo = Integer.parseInt(n1);
			//foo--;
			time = time.substring(0, 5);
			sb.append(foo).append(" ");
			sb.append(time).append(":00.0").append(",");;
				
			sb.append("NOT_STARTED").append(",");
			sb.append("IPL8").append(",");
			
			String stadium = m_potStadium.get(i).textNodes().get(0).toString();
			sb.append(stadium.replace(", "," ").trim()).append(",");
			
			sb.append("25").append(",");
			sb.append("OPEN");
			
			sb.append("\n");
			System.out.print(sb.toString());
		}
		
		System.out.print(sb.toString());
		
	}

	public void getScore() {
		Elements scores = m_potLiveScore.get(0).getElementsByClass("espni-livescores-scoreline");
		Elements teamname,teamscore,starttime;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<scores.size();i++){
			Element l1 = scores.get(i).child(0);
			teamname = l1.getElementsByClass("team-name");
			teamscore = l1.getElementsByClass("team-score");
			starttime = l1.getElementsByClass("start-time");
			

			Element tname = teamname.get(0);
			Element tscore = teamscore.get(0);
			
			sb.append(tname.textNodes().get(0));
			
			if(!tscore.textNodes().isEmpty())
				sb.append(tscore.textNodes().get(0));
			
			sb.append(" vs ");
			
			tname = teamname.get(1);
			tscore = teamscore.get(1);
			
			sb.append(tname.textNodes().get(0));
			
			if(!tscore.textNodes().isEmpty())
				sb.append(tscore.textNodes().get(0));
			
			sb.append(" - ");
			
			tname = starttime.get(0);
			if(!tname.textNodes().isEmpty()){
				sb.append(tname.textNodes().get(0));
			}
			
			sb.append("\n");
			System.out.println();
			
		}
		System.out.println(sb.toString().replaceAll("&nbsp;ov", "").replaceAll("&amp;", ""));
	}
}
