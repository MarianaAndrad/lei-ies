package pt.ua.ies103823.WeatherForecastByCity;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    //todo: should generalize for a city passed as argument
    private static final int CITY_ID_AVEIRO = 1010500;

    public static void  main(String[] args ) {
        int City_ID = -1;
        if (args.length != 1){
            System.out.println("No city id provided, using Aveiro as default");
            City_ID = CITY_ID_AVEIRO;
        } else{
            System.out.println("ID da Cidade: " + args[0]);
            City_ID = Integer.parseInt(args[0]);
        }

        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create a typed interface to use the remote API (a client)
        IpmaService service = retrofit.create(IpmaService.class);
        // prepare the call to remote endpoint
        Call<IpmaCityForecast> callSync = service.getForecastForACity(City_ID);

        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
