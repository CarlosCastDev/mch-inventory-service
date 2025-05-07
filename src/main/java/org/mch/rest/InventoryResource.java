package org.mch.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.mch.entity.InventoryItem;
import org.mch.service.InventoryService;

import java.util.Collections;
import java.util.List;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryResource {

    @Inject
    InventoryService inventoryService;

    @GET
    public List<InventoryItem> getAllItems() {
        return InventoryItem.listAll();
    }

    @GET
    @Path("/{id}")
    public InventoryItem getItem(@PathParam("id") Long id) {
        InventoryItem item = InventoryItem.findById(id);
        if (item == null) {
            throw new NotFoundException("Producto no encontrado");
        }
        return item;
    }

    @POST
    @Transactional
    public Response createItem(InventoryItem item, @Context UriInfo uriInfo) {
        item.persist();
        if (item.isPersistent()) {
            UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(item.id.toString());
            return Response.created(builder.build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public InventoryItem updateItem(@PathParam("id") Long id, InventoryItem updatedItem) {
        InventoryItem item = InventoryItem.findById(id);
        if (item == null) {
            throw new NotFoundException("Producto no encontrado");
        }
        item.productName = updatedItem.productName;
        item.productDescription = updatedItem.productDescription;
        item.quantity = updatedItem.quantity;
        item.price = updatedItem.price;
        item.expirationDate = updatedItem.expirationDate;
        return item;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteItem(@PathParam("id") Long id) {
        InventoryItem item = InventoryItem.findById(id);
        if (item == null) {
            throw new NotFoundException("Producto no encontrado");
        }
        item.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("/check-stock/{medicineId}/{quantity}")
    public Response checkStock(@PathParam("medicineId") Long medicineId, @PathParam("quantity") int quantity) {
        boolean hasStock = inventoryService.hasSufficientStock(medicineId, quantity);

        if (hasStock) {
            return Response.ok().build(); // 200 OK
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Collections.singletonMap("message", "Stock insuficiente"))
                    .build(); // 400 Bad Request
        }
    }
}

