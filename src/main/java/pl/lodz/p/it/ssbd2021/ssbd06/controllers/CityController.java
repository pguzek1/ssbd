package pl.lodz.p.it.ssbd2021.ssbd06.controllers;

import pl.lodz.p.it.ssbd2021.ssbd06.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2021.ssbd06.moh.dto.CityDto;

import javax.ws.rs.*;
import java.util.List;

/**
 * Kontroler odpowiadający za zarządzanie miastami.
 */
@Path("/cities/")
public class CityController extends AbstractController {
    /**
     * Zwraca miasto o podanym identyfikatorze
     *
     * @param id identyfikator miasta
     * @throws AppBaseException podczas błędu związanego z pobieraniem miasta
     * @return dto miasta
     */
    @GET
    @Path("/{id}")
    public CityDto get(@PathParam("id") Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca listę miast
     *
     * @throws AppBaseException podczas błędu związanego z pobieraniem listy miast
     * @return lista dto miast
     */
    @GET
    public List<CityDto> getAll() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Dodaje miasto
     *
     * @param cityDto dto z danymi miasta
     * @throws AppBaseException podczas błędu związanego z dodawaniem miasta
     */
    @POST
    public void addCity(CityDto cityDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Modyfikuje miasto
     *
     * @param cityDto dto z danymi miasta
     * @throws AppBaseException podczas błędu związanego z aktualizacją miasta
     */
    @PUT
    public void updateCity(CityDto cityDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Usuwa miasto
     *
     * @param cityId identyfikator miasta
     * @throws AppBaseException podczas błędu związanego z usuwaniem miasta
     */
    @DELETE
    @Path("/{id}")
    public void deleteCity(@PathParam("id") Long cityId) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}