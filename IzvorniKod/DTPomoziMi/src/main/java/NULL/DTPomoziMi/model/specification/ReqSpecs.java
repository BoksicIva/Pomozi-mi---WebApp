package NULL.DTPomoziMi.model.specification;

import org.springframework.data.jpa.domain.Specification;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.model.User;

public class ReqSpecs {
	public static Specification<Request> statusEqual(RequestStatus status) {
		return (root, query, builder) -> {
			if (status == null) return builder.conjunction();// nemoj filtrirat ak je null

			return builder.equal(root.<RequestStatus>get("status"), status);
		};
	}

	public static Specification<Request> authorEqual(User author) {
		return (root, query, builder) -> {
			if (author == null) return builder.conjunction();// nemoj filtrirat ak je null

			return builder.equal(root.<User>get("author"), author);
		};
	}

	public static Specification<Request> executorEqual(User executor) {
		return (root, query, builder) -> {
			if (executor == null) return builder.conjunction();// nemoj filtrirat ak je null

			return builder.equal(root.<User>get("executor"), executor);
		};
	}
}
