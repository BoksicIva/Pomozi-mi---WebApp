package NULL.DTPomoziMi.model.specification;

import org.springframework.data.jpa.domain.Specification;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.model.RequestStatus;

public class ReqSpecs {
	public static Specification<Request> statusEqual(RequestStatus status) {
		return (root, query, builder) -> {
			if (status == null) return builder.conjunction();// nemoj filtrirat ak je null

			return builder.equal(root.<RequestStatus>get("status"), status);
		};
	}

	public static <R, P> Specification<R> atributeEqualNotEqual(String attribute, P type, boolean equal) {
		return (root, query, builder) -> {
			if (type == null) return builder.conjunction();// nemoj filtrirat ak je null

			if (equal)
				return builder.equal(root.<P>get(attribute), type);
			else
				return builder.notEqual(root.<P>get(attribute), type);
		};
	}
}
