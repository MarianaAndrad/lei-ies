package pt.ua.ies103823.WeatherForecastByCity;

import pt.ua.ies103823.IpmaClient.*;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    //todo: should generalize for a city passed as argument
    private static final int CITY_ID_AVEIRO = 1010500;

    public static void  main(String[] args ) {
        String City = null;
        if (args.length != 1){
            System.out.println("No city id provided, using Aveiro as default");
            City = "Aveiro";
        } else{
            System.out.println("Cidade: " + args[0]);
            City = args[0];
        }

        IpmaApiLogic ipma = new IpmaApiLogic();

        IpmaCity cidades = ipma.getCity();
        int City_id = -1;
        for (int i = 0; i < cidades.getData().size(); i++) {
            if (cidades.getData().get(i).getLocal().equals(City)){
                System.out.println("Cidade: " + cidades.getData().get(i).getLocal());
                City_id = cidades.getData().get(i).getGlobalIdLocal();
            }
        }
        if(City_id == -1){
            System.err.println("Cidade não encontrada");
            System.exit(1);
        }

        IpmaCityForecast forecast = ipma.getWeatherForecastByCity(City_id);

        if (forecast != null) {
                //colocar para diferentes datas
                System.out.println("Na cidade com ID " + forecast.getGlobalIdLocal() + " no país " + forecast.getCountry()+ ".");

                System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%10s | %10s | %10s | %10s | %10s | %10s | %10s %n", "DATE", "TEMP MAX", "TEMP MIN", "PROB PRECI", "DIR VENTO", "VEL VENTO", "ID TEMPO");
                System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                for (CityForecast day : forecast.getData()) {
                    System.out.printf("%10s | %8.1fºC | %8.1fºC | %8.2f%% | %10s | %10s | %10s %n",
                            day.getForecastDate(),
                            Double.parseDouble(day.getTMax()),
                            Double.parseDouble(day.getTMin()),
                            Double.parseDouble(day.getPrecipitaProb()),
                            day.getPredWindDir(),
                            day.getClassWindSpeed(),
                            day.getIdWeatherType());
                }
                System.out.println("----------------------------------------------------------------------------------------------------------------------------");

            } else {
                System.out.println( "No results for this request!");
            }
    }
}
