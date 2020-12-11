package NULL.DTPomoziMi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity(name = "uloga")
@Table(name = "uloga")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = -6873258326473510073L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_uloga")
	private Long IdRole;

	@Enumerated(EnumType.STRING)
	@Column(name = "naziv")
	private Role role;

	@Exclude
	@ManyToMany
	@JoinTable(
			name = "imaulogu", joinColumns = { @JoinColumn(name = "id_uloga") },
			inverseJoinColumns = { @JoinColumn(name = "id_korisnik") }
	)
	private Set<User> users = new HashSet<>();

}
