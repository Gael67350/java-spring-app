package eu.gael67350.api.interfaces;

public interface DtoConvertable<Entity, Dto> {
	
	/**
	 * Convert an application entity into a data transfer object.
	 * 
	 * @param entity	The entity to convert into DTO
	 * @return			The converted DTO
	 */
	public Dto convertToDto(Entity entity);
	
	/**
	 * Convert a data transfer object into an application entity.
	 * 
	 * @param dto	The data transfer object
	 * @return		The converted entity
	 */
	public Entity convertToEntity(Dto dto);
	
}
